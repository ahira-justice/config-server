package com.ahirajustice.configserver.modules.microservice.services.impl;

import com.ahirajustice.configserver.common.constants.KeyConstants;
import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.exceptions.NotFoundException;
import com.ahirajustice.configserver.common.properties.AppProperties;
import com.ahirajustice.configserver.common.repositories.MicroserviceRepository;
import com.ahirajustice.configserver.common.utils.AuthUtils;
import com.ahirajustice.configserver.common.utils.CommonUtils;
import com.ahirajustice.configserver.modules.microservice.queries.SearchMicroservicesQuery;
import com.ahirajustice.configserver.modules.microservice.requests.CreateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.requests.UpdateMicroserviceRequest;
import com.ahirajustice.configserver.modules.microservice.services.MicroserviceService;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceDetail;
import com.ahirajustice.configserver.modules.microservice.viewmodels.MicroserviceViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MicroserviceServiceImpl implements MicroserviceService {

    private final MicroserviceRepository microserviceRepository;
    private final AppProperties appProperties;

    @Override
    public Page<MicroserviceViewModel> searchMicroservices(SearchMicroservicesQuery query) {
        return microserviceRepository.findAll(query.getPredicate(), query.getPageable()).map(MicroserviceViewModel::from);
    }

    @Override
    public MicroserviceDetail getMicroservice(long id) {
        Microservice microservice = validateMicroservice(id);
        var decryptedSecretKey = AuthUtils.decryptString(microservice.getEncryptedSecretKey(), appProperties.getPrivateKey());
        return MicroserviceDetail.from(microservice, decryptedSecretKey);
    }

    @Override
    public MicroserviceDetail createMicroservice(CreateMicroserviceRequest request) {
        validateCreateMicroserviceRequest(request);
        Microservice microservice = buildMicroservice(request);
        return persistMicroservice(microservice);
    }

    private void validateCreateMicroserviceRequest(CreateMicroserviceRequest request) {
        if (microserviceRepository.existsByIdentifier(request.getIdentifier()))
            throw new BadRequestException(String.format("Microservice with identifier: '%s' already exists", request.getIdentifier()));
    }

    private Microservice buildMicroservice(CreateMicroserviceRequest request) {
        var splitSecretKey = generateSecretKey().split("\\|");
        String hashedSecretKey = splitSecretKey[0];
        String encryptedSecretKey = splitSecretKey[1];

        Microservice microservice = Microservice.builder()
                .identifier(request.getIdentifier())
                .hashedSecretKey(hashedSecretKey)
                .encryptedSecretKey(encryptedSecretKey)
                .encryptingKey(request.getEncryptingKey())
                .isActive(true)
                .build();

        microservice.setRestartConfig(request.getRestartConfig());

        return microservice;
    }

    private String generateSecretKey() {
        String secretKey;
        String encryptedSecretKey;

        do {
            secretKey = CommonUtils.generateRandomHex(
                    KeyConstants.SECRET_KEY_PREFIX, appProperties.getSecretKeyLength()
            );
        }
        while (secretKeyExists(AuthUtils.getSha256Hash(secretKey)));

        encryptedSecretKey = AuthUtils.encryptString(secretKey, appProperties.getPublicKey());

        return AuthUtils.getSha256Hash(secretKey) + "|" + encryptedSecretKey;
    }

    private boolean secretKeyExists(String hashedSecretKey) {
        return microserviceRepository.existsByHashedSecretKey(hashedSecretKey);
    }

    @Override
    public MicroserviceDetail updateMicroservice(UpdateMicroserviceRequest request, long id) {
        Microservice microservice = validateMicroservice(id);
        microservice.setRestartConfig(request.getRestartConfig());
        return persistMicroservice(microservice);
    }

    @Override
    public MicroserviceDetail changeMicroserviceSecretKey(long id) {
        Microservice microservice = validateMicroservice(id);

        var splitSecretKey = generateSecretKey().split("\\|");
        String hashedSecretKey = splitSecretKey[0];
        String encryptedSecretKey = splitSecretKey[1];

        microservice.setHashedSecretKey(hashedSecretKey);
        microservice.setEncryptedSecretKey(encryptedSecretKey);

        return persistMicroservice(microservice);
    }

    private Microservice validateMicroservice(long id) {
        Microservice microservice = microserviceRepository.findById(id).orElse(null);

        if (microservice == null)
            throw new NotFoundException(String.format("Microservice with id: '%d' does not exist", id));

        return microservice;
    }

    private MicroserviceDetail persistMicroservice(Microservice microservice) {
        Microservice updatedMicroservice = microserviceRepository.save(microservice);
        var decryptedSecretKey = AuthUtils.decryptString(microservice.getEncryptedSecretKey(), appProperties.getPrivateKey());
        return MicroserviceDetail.from(updatedMicroservice, decryptedSecretKey);
    }

    @Override
    public Microservice validateMicroservice(String identifier) {
        Microservice microservice = microserviceRepository.findByIdentifier(identifier).orElse(null);

        if (microservice == null)
            throw new NotFoundException(String.format("Microservice with identifier: '%s' does not exist", identifier));

        return microservice;
    }

}
