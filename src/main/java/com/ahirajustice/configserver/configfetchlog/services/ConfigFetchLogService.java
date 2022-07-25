package com.ahirajustice.configserver.configfetchlog.services;

import com.ahirajustice.configserver.configfetchlog.queries.SearchConfigFetchLogQuery;
import com.ahirajustice.configserver.configfetchlog.viewmodels.ConfigFetchLogViewModel;
import org.springframework.data.domain.Page;

public interface ConfigFetchLogService {

    Page<ConfigFetchLogViewModel> searchConfigFetchLog(SearchConfigFetchLogQuery query);

}
