package com.ahirajustice.configserver.config.services.impl;

import com.ahirajustice.configserver.client.services.CurrentClientService;
import com.ahirajustice.configserver.common.entities.Client;
import com.ahirajustice.configserver.common.entities.Config;
import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.repositories.ConfigRepository;
import com.ahirajustice.configserver.config.queries.SearchConfigsQuery;
import com.ahirajustice.configserver.config.requests.BatchCreateConfigsRequest;
import com.ahirajustice.configserver.config.requests.CreateConfigRequest;
import com.ahirajustice.configserver.config.services.ConfigService;
import com.ahirajustice.configserver.config.viewmodels.ConfigViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;
    private final CurrentClientService currentClientService;

    @Override
    public Page<ConfigViewModel> searchConfigs(SearchConfigsQuery query) {
        return configRepository.findAll(query.getPredicate(), query.getPageable()).map(ConfigViewModel::from);
    }

    @Override
    public List<ConfigViewModel> fetchConfigs(ConfigEnvironment environment) {
        Client currentClient = currentClientService.getCurrentClient();

        List<Config> configs = configRepository.findByClientAndConfigEnvironment(currentClient, environment);

        return configs.stream().map(ConfigViewModel::from).collect(Collectors.toList());
    }

    @Override
    public void createConfig(CreateConfigRequest request) {
        Client currentClient = currentClientService.getCurrentClient();

        if (configRepository.existsByKeyAndClientAndConfigEnvironment(request.getKey(), currentClient, request.getConfigEnvironment())){
            String msg = String.format(
                    "Config with key %s in %s environment already exists for client", request.getKey(), request.getConfigEnvironment()
            );
            throw new BadRequestException(msg);
        }

        Config config = buildConfig(request);
        config.setClient(currentClient);

        configRepository.save(config);
    }

    private Config buildConfig(CreateConfigRequest request) {
        return Config.builder()
                .key(request.getKey())
                .value(request.getValue())
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

}
