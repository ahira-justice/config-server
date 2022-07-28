package com.ahirajustice.configserver.config.services.impl;

import com.ahirajustice.configserver.client.services.CurrentClientService;
import com.ahirajustice.configserver.common.entities.Client;
import com.ahirajustice.configserver.common.entities.Config;
import com.ahirajustice.configserver.common.entities.ConfigFetchLog;
import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
import com.ahirajustice.configserver.common.error.Error;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.exceptions.ConfigurationException;
import com.ahirajustice.configserver.common.exceptions.FailedDependencyException;
import com.ahirajustice.configserver.common.exceptions.ValidationException;
import com.ahirajustice.configserver.common.repositories.ConfigFetchLogRepository;
import com.ahirajustice.configserver.common.repositories.ConfigRepository;
import com.ahirajustice.configserver.common.responses.SimpleMessageResponse;
import com.ahirajustice.configserver.common.utils.CommonUtils;
import com.ahirajustice.configserver.common.utils.ObjectMapperUtil;
import com.ahirajustice.configserver.config.queries.SearchConfigsQuery;
import com.ahirajustice.configserver.config.requests.BatchCreateConfigsRequest;
import com.ahirajustice.configserver.config.requests.CreateConfigRequest;
import com.ahirajustice.configserver.config.requests.RefreshConfigsRequest;
import com.ahirajustice.configserver.config.services.ConfigService;
import com.ahirajustice.configserver.config.responses.ConfigEntry;
import com.ahirajustice.configserver.config.viewmodels.ConfigViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;
    private final ConfigFetchLogRepository configFetchLogRepository;
    private final CurrentClientService currentClientService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public Page<ConfigViewModel> searchConfigs(SearchConfigsQuery query) {
        return configRepository.findAll(query.getPredicate(), query.getPageable()).map(ConfigViewModel::from);
    }

    @Override
    public List<ConfigEntry> fetchConfigs(ConfigEnvironment configEnvironment) {
        Client currentClient = currentClientService.getCurrentClient();
        return fetchConfigs(configEnvironment, currentClient);
    }

    private List<ConfigEntry> fetchConfigs(ConfigEnvironment configEnvironment, Client currentClient) {
        List<Config> configs = configRepository.findByClientAndConfigEnvironment(currentClient, configEnvironment);

        List<ConfigEntry> response = configs.stream().map(ConfigEntry::from).collect(Collectors.toList());

        logConfigFetch(configEnvironment, currentClient, response);

        return response;
    }

    private void logConfigFetch(ConfigEnvironment configEnvironment, Client client, List<ConfigEntry> response) {
        ConfigFetchLog configFetchLog = ConfigFetchLog.builder()
                .client(client)
                .configEnvironment(configEnvironment)
                .retrievedConfig(ObjectMapperUtil.serialize(objectMapper, response))
                .build();

        configFetchLogRepository.save(configFetchLog);
    }

    @Override
    public void createConfig(CreateConfigRequest request) {
        validate(request.getKey());
        Client currentClient = currentClientService.getCurrentClient();

        if (configRepository.existsByConfigKeyAndClientAndConfigEnvironment(request.getKey(), currentClient, request.getConfigEnvironment())){
            String msg = String.format(
                    "Config with key %s in %s environment already exists for client '%s'", request.getKey(), request.getConfigEnvironment(), currentClient.getIdentifier()
            );
            throw new BadRequestException(msg);
        }

        Config config = buildConfig(request);
        config.setClient(currentClient);

        configRepository.save(config);
    }

    private void validate(String key) {
        List<Error> errors = new ArrayList<>();

        if (StringUtils.containsWhitespace(key))
            errors.add(Error.create("key", "key must not contain spaces", key));

        if (CommonUtils.containsSpecialCharactersAndNumbers(key))
            errors.add(Error.create("key", "key must not contain special characters or numbers", key));

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }

    private Config buildConfig(CreateConfigRequest request) {
        return Config.builder()
                .configKey(request.getKey())
                .configValue(request.getValue())
                .configEnvironment(request.getConfigEnvironment())
                .build();
    }

    @Transactional
    @Override
    public void batchCreateConfigs(BatchCreateConfigsRequest batchRequest) {
        for (var request: batchRequest.getRequests()) {
            createConfig(request);
        }
    }

    @Override
    public SimpleMessageResponse refreshConfigs(RefreshConfigsRequest request) {
        Client currentClient = currentClientService.getCurrentClient();

        List<ConfigEntry> configEntries = fetchConfigs(request.getEnvironment(), currentClient);

        HttpEntity<?> requestEntity = new HttpEntity<>(configEntries);

        try {
            var responseEntity = restTemplate.exchange(
                    currentClient.getRefreshCallbackUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    SimpleMessageResponse.class
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException ex) {
            if (ex instanceof HttpClientErrorException.NotFound)
                throw new BadRequestException("Client's refreshCallbackUrl improperly configured");
            else if (ex instanceof HttpClientErrorException.BadRequest || ex instanceof HttpClientErrorException.UnprocessableEntity)
                throw new ConfigurationException(String.format("Bad refresh implementation on client '%s'", currentClient.getIdentifier()));
            else
                throw new ConfigurationException(ex.getMessage());
        }
        catch (ResourceAccessException ex) {
            throw new FailedDependencyException("Cannot reach client application. Please try again later");
        }
    }

}
