package com.ahirajustice.configserver.modules.config.controllers;

import com.ahirajustice.configserver.common.responses.SimpleMessageResponse;
import com.ahirajustice.configserver.modules.config.queries.SearchConfigsQuery;
import com.ahirajustice.configserver.modules.config.requests.BatchCreateConfigsRequest;
import com.ahirajustice.configserver.modules.config.requests.CreateConfigRequest;
import com.ahirajustice.configserver.modules.config.responses.ConfigEntry;
import com.ahirajustice.configserver.modules.config.services.ConfigService;
import com.ahirajustice.configserver.modules.config.viewmodels.ConfigViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<ConfigViewModel> searchConfigs(SearchConfigsQuery query) {
        return configService.searchConfigs(query);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createConfig(@Valid @RequestBody CreateConfigRequest request) {
        configService.createConfig(request);
    }

    @RequestMapping(path = "/fetch", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ConfigEntry> fetchConfigs() {
        return configService.fetchConfigs();
    }

    @RequestMapping(path = "/batch", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void batchCreateConfigs(@Valid @RequestBody BatchCreateConfigsRequest request) {
        configService.batchCreateConfigs(request);
    }

    @RequestMapping(path = "/refresh/{serviceIdentifier}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public SimpleMessageResponse refreshConfigs(@PathVariable String serviceIdentifier) {
        return configService.refreshConfigs(serviceIdentifier);
    }

}
