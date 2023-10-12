package com.ahirajustice.configserver.modules.configfetchlog.controllers;

import com.ahirajustice.configserver.modules.configfetchlog.queries.SearchConfigFetchLogQuery;
import com.ahirajustice.configserver.modules.configfetchlog.services.ConfigFetchLogService;
import com.ahirajustice.configserver.modules.configfetchlog.viewmodels.ConfigFetchLogViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/config-fetch-log")
@RequiredArgsConstructor
public class ConfigFetchLogController {

    private final ConfigFetchLogService configFetchLogService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<ConfigFetchLogViewModel> searchConfigs(SearchConfigFetchLogQuery query) {
        return configFetchLogService.searchConfigFetchLog(query);
    }

}
