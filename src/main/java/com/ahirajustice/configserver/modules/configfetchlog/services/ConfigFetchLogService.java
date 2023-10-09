package com.ahirajustice.configserver.modules.configfetchlog.services;

import com.ahirajustice.configserver.modules.configfetchlog.queries.SearchConfigFetchLogQuery;
import com.ahirajustice.configserver.modules.configfetchlog.viewmodels.ConfigFetchLogViewModel;
import org.springframework.data.domain.Page;

public interface ConfigFetchLogService {

    Page<ConfigFetchLogViewModel> searchConfigFetchLog(SearchConfigFetchLogQuery query);

}
