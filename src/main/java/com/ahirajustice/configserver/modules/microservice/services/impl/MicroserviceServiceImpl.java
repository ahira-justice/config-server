package com.ahirajustice.configserver.modules.microservice.services.impl;

import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.exceptions.NotFoundException;
import com.ahirajustice.configserver.common.repositories.MicroserviceRepository;
import com.ahirajustice.configserver.modules.microservice.queries.SearchMicroservicesQuery;
import com.ahirajustice.configserver.modules.microservice.requests.CreateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.requests.UpdateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.services.MicroserviceService;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MicroserviceServiceImpl implements MicroserviceService {

    private final MicroserviceRepository microserviceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Page<MicroserviceViewModel> searchMicroservices(SearchMicroservicesQuery query) {
        return microserviceRepository.findAll(query.getPredicate(), query.getPageable()).map(MicroserviceViewModel::from);
    }

    @Override
    public MicroserviceViewModel getMicroservice(long id) {
        Optional<Microservice> microserviceExists = microserviceRepository.findById(id);

        if (microserviceExists.isEmpty()) {
            throw new NotFoundException(String.format("Microservice with id: '%d' does not exist", id));
        }

        return MicroserviceViewModel.from(microserviceExists.get());
    }

    @Override
    public MicroserviceViewModel createMicroservice(CreateMicroserviceRequest request) {
        if (microserviceRepository.existsByIdentifier(request.getIdentifier())) {
            throw new BadRequestException(String.format("Microservice with identifier: '%s' already exists", request.getIdentifier()));
        }

        Microservice microservice = buildMicroservice(request);
        microservice.setActive(true);

        return MicroserviceViewModel.from(microserviceRepository.save(microservice));
    }

    private Microservice buildMicroservice(CreateMicroserviceRequest request) {
        return Microservice.builder()
                .identifier(request.getIdentifier())
                .secretKey(passwordEncoder.encode(request.getSecretKey()))
                .baseUrl(request.getBaseUrl())
                .encryptingKey(request.getEncryptingKey())
                .build();
    }

    @Override
    public MicroserviceViewModel updateMicroservice(UpdateMicroserviceRequest request, long id) {
        Microservice microservice = microserviceRepository.findById(id).orElse(null);

        if (microservice == null) {
            throw new NotFoundException(String.format("Microservice with id: '%d' does not exist", id));
        }

        microservice.setBaseUrl(request.getBaseUrl());

        return MicroserviceViewModel.from(microserviceRepository.save(microservice));
    }

}
