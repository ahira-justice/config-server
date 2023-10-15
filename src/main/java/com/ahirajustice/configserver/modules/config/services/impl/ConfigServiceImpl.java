package com.ahirajustice.configserver.modules.config.services.impl;

import com.ahirajustice.configserver.common.entities.Config;
import com.ahirajustice.configserver.common.entities.ConfigFetchLog;
import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.common.error.Error;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.exceptions.ConfigurationException;
import com.ahirajustice.configserver.common.exceptions.FailedDependencyException;
import com.ahirajustice.configserver.common.exceptions.ValidationException;
import com.ahirajustice.configserver.common.repositories.ConfigFetchLogRepository;
import com.ahirajustice.configserver.common.repositories.ConfigRepository;
import com.ahirajustice.configserver.common.repositories.MicroserviceRepository;
import com.ahirajustice.configserver.common.responses.SimpleMessageResponse;
import com.ahirajustice.configserver.common.utils.AuthUtils;
import com.ahirajustice.configserver.common.utils.CommonUtils;
import com.ahirajustice.configserver.common.utils.ObjectMapperUtils;
import com.ahirajustice.configserver.modules.config.queries.SearchConfigsQuery;
import com.ahirajustice.configserver.modules.config.requests.BatchCreateConfigsRequest;
import com.ahirajustice.configserver.modules.config.requests.CreateConfigRequest;
import com.ahirajustice.configserver.modules.config.responses.ConfigEntry;
import com.ahirajustice.configserver.modules.config.services.ConfigService;
import com.ahirajustice.configserver.modules.config.viewmodels.ConfigViewModel;
import com.ahirajustice.configserver.modules.microservice.services.CurrentMicroserviceService;
import com.ahirajustice.configserver.modules.microservice.services.MicroserviceService;
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
    private final CurrentMicroserviceService currentMicroserviceService;
    private final MicroserviceService microserviceService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public Page<ConfigViewModel> searchConfigs(SearchConfigsQuery query) {
        return configRepository.findAll(query.getPredicate(), query.getPageable()).map(ConfigViewModel::from);
    }

    @Override
    public List<ConfigEntry> fetchConfigs() {
        Microservice currentMicroservice = currentMicroserviceService.getCurrentMicroservice();
        return fetchConfigs(currentMicroservice);
    }

    private List<ConfigEntry> fetchConfigs(Microservice currentMicroservice) {
        List<Config> configs = configRepository.findByMicroservice(currentMicroservice);

        List<ConfigEntry> response = configs.stream().map(ConfigEntry::from).collect(Collectors.toList());

        logConfigFetch(currentMicroservice, response);

        return response;
    }

    private void logConfigFetch(Microservice microservice, List<ConfigEntry> response) {
        ConfigFetchLog configFetchLog = ConfigFetchLog.builder()
                .microservice(microservice)
                .retrievedConfig(ObjectMapperUtils.serialize(objectMapper, response))
                .build();

        configFetchLogRepository.save(configFetchLog);
    }

    @Override
    public void createConfig(CreateConfigRequest request) {
        validate(request.getKey());
        Microservice currentMicroservice = currentMicroserviceService.getCurrentMicroservice();

        if (configRepository.existsByConfigKeyAndMicroservice(request.getKey(), currentMicroservice)){
            String msg = String.format(
                    "Config with key %s already exists for microservice '%s'", request.getKey(), currentMicroservice.getIdentifier()
            );
            throw new BadRequestException(msg);
        }

        Config config = buildConfig(request, currentMicroservice);

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

    private Config buildConfig(CreateConfigRequest request, Microservice microservice) {
        String configValue = request.getValue();

        if (request.encrypt()) {
            if (StringUtils.isBlank(microservice.getEncryptingKey()))
                throw new BadRequestException("Microservice public key is not set. Cannot encrypt config.");

            configValue = AuthUtils.encryptString(configValue, microservice.getEncryptingKey());
        }

        return Config.builder()
                .configKey(request.getKey())
                .configValue(configValue)
                .encrypted(request.encrypt())
                .microservice(microservice)
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
    public SimpleMessageResponse refreshConfigs(String serviceIdentifier) {
        Microservice currentMicroservice = microserviceService.validateMicroservice(serviceIdentifier);

        List<ConfigEntry> configEntries = fetchConfigs(currentMicroservice);

        HttpEntity<?> requestEntity = new HttpEntity<>(configEntries);

        try {
            var responseEntity = restTemplate.exchange(
                    String.format("%s/refresh", currentMicroservice.getBaseUrl()),
                    HttpMethod.POST,
                    requestEntity,
                    SimpleMessageResponse.class
            );

            return responseEntity.getBody();
        }
        catch (HttpClientErrorException ex) {
            if (ex instanceof HttpClientErrorException.NotFound)
                throw new BadRequestException("Microservice's baseUrl improperly configured");
            else if (ex instanceof HttpClientErrorException.BadRequest || ex instanceof HttpClientErrorException.UnprocessableEntity)
                throw new ConfigurationException(String.format("Bad refresh implementation on microservice '%s'", currentMicroservice.getIdentifier()));
            else
                throw new ConfigurationException(ex.getMessage());
        }
        catch (ResourceAccessException ex) {
            throw new FailedDependencyException("Cannot reach microservice application. Please try again later");
        }
    }

}
