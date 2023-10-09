package com.ahirajustice.configserver.modules.configfetchlog.services.impl;

import com.ahirajustice.configserver.common.repositories.ConfigFetchLogRepository;
import com.ahirajustice.configserver.modules.configfetchlog.queries.SearchConfigFetchLogQuery;
import com.ahirajustice.configserver.modules.configfetchlog.services.ConfigFetchLogService;
import com.ahirajustice.configserver.modules.configfetchlog.viewmodels.ConfigFetchLogViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigFetchLogServiceImpl implements ConfigFetchLogService {

    private final ConfigFetchLogRepository configFetchLogRepository;

    @Override
    public Page<ConfigFetchLogViewModel> searchConfigFetchLog(SearchConfigFetchLogQuery query) {
        return configFetchLogRepository.findAll(query.getPredicate(), query.getPageable()).map(ConfigFetchLogViewModel::from);
    }

}
