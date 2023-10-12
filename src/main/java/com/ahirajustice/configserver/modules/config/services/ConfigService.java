package com.ahirajustice.configserver.modules.config.services;

import com.ahirajustice.configserver.common.responses.SimpleMessageResponse;
import com.ahirajustice.configserver.modules.config.queries.SearchConfigsQuery;
import com.ahirajustice.configserver.modules.config.requests.BatchCreateConfigsRequest;
import com.ahirajustice.configserver.modules.config.requests.CreateConfigRequest;
import com.ahirajustice.configserver.modules.config.responses.ConfigEntry;
import com.ahirajustice.configserver.modules.config.viewmodels.ConfigViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConfigService {

    Page<ConfigViewModel> searchConfigs(SearchConfigsQuery query);

    List<ConfigEntry> fetchConfigs();

    void createConfig(CreateConfigRequest request);

    void batchCreateConfigs(BatchCreateConfigsRequest batchRequest);

    SimpleMessageResponse refreshConfigs();

}
