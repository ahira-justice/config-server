package com.ahirajustice.configserver.modules.microservice.services;

import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.modules.microservice.queries.SearchMicroservicesQuery;
import com.ahirajustice.configserver.modules.microservice.requests.CreateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.requests.UpdateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceDetail;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceViewModel;
import org.springframework.data.domain.Page;

public interface MicroserviceService {

    Page<MicroserviceViewModel> searchMicroservices(SearchMicroservicesQuery query);
    MicroserviceDetail getMicroservice(long id);
    MicroserviceDetail createMicroservice(CreateMicroserviceRequest request);
    MicroserviceDetail updateMicroservice(UpdateMicroserviceRequest request, long id);
    MicroserviceDetail changeMicroserviceSecretKey(long id);
    Microservice validateMicroservice(String identifier);

}
