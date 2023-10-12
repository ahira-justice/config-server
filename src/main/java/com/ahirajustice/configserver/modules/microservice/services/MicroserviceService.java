package com.ahirajustice.configserver.modules.microservice.services;

import com.ahirajustice.configserver.modules.microservice.queries.SearchMicroservicesQuery;
import com.ahirajustice.configserver.modules.microservice.requests.CreateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.requests.UpdateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceViewModel;
import org.springframework.data.domain.Page;

public interface MicroserviceService {

    Page<MicroserviceViewModel> searchMicroservices(SearchMicroservicesQuery query);

    MicroserviceViewModel getMicroservice(long id);

    MicroserviceViewModel createMicroservice(CreateMicroserviceRequest request);

    MicroserviceViewModel updateMicroservice(UpdateMicroserviceRequest request, long id);

}
