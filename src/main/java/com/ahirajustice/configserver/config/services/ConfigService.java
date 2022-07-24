package com.ahirajustice.configserver.config.services;

import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
import com.ahirajustice.configserver.config.queries.SearchConfigsQuery;
import com.ahirajustice.configserver.config.requests.BatchCreateConfigsRequest;
import com.ahirajustice.configserver.config.requests.CreateConfigRequest;
import com.ahirajustice.configserver.config.responses.ConfigEntry;
import com.ahirajustice.configserver.config.viewmodels.ConfigViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConfigService {

    Page<ConfigViewModel> searchConfigs(SearchConfigsQuery query);

    List<ConfigEntry> fetchConfigs(ConfigEnvironment configEnvironment);

    void createConfig(CreateConfigRequest request);

    void batchCreateConfigs(BatchCreateConfigsRequest batchRequest);

}
