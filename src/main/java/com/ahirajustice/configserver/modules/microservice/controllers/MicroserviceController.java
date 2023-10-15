package com.ahirajustice.configserver.modules.microservice.controllers;

import com.ahirajustice.configserver.modules.microservice.queries.SearchMicroservicesQuery;
import com.ahirajustice.configserver.modules.microservice.requests.CreateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.requests.UpdateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.services.MicroserviceService;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceDetail;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceViewModel;
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

@RestController
@RequestMapping("api/microservices")
@RequiredArgsConstructor
public class MicroserviceController {

    private final MicroserviceService microserviceService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<MicroserviceViewModel> searchMicroservices(SearchMicroservicesQuery query) {
        return microserviceService.searchMicroservices(query);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MicroserviceDetail getMicroservice(@PathVariable long id) {
        return microserviceService.getMicroservice(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public MicroserviceDetail createMicroservice(@Valid @RequestBody CreateMicroserviceRequest request) {
        return microserviceService.createMicroservice(request);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public MicroserviceDetail updateMicroservice(@PathVariable long id, @Valid @RequestBody UpdateMicroserviceRequest request) {
        return microserviceService.updateMicroservice(request, id);
    }

    @RequestMapping(path = "/{id}/change-secret-key", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public MicroserviceDetail changeMicroserviceSecretKey(@PathVariable long id) {
        return microserviceService.changeMicroserviceSecretKey(id);
    }

}
