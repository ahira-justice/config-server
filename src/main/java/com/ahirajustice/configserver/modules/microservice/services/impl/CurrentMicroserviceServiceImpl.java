package com.ahirajustice.configserver.modules.microservice.services.impl;

import com.ahirajustice.configserver.common.constants.SecurityConstants;
import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.common.exceptions.SystemErrorException;
import com.ahirajustice.configserver.common.exceptions.ValidationException;
import com.ahirajustice.configserver.common.repositories.MicroserviceRepository;
import com.ahirajustice.configserver.modules.auth.services.AuthDecodeService;
import com.ahirajustice.configserver.modules.microservice.services.CurrentMicroserviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentMicroserviceServiceImpl implements CurrentMicroserviceService {

    private final HttpServletRequest request;
    private final MicroserviceRepository microserviceRepository;
    private final AuthDecodeService authDecodeService;

    @Override
    public Microservice getCurrentMicroservice() {
        try {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            Optional<String> microserviceIdExists = getMicroserviceIdFromToken(header);
            if (!microserviceIdExists.isPresent())
                throw new ValidationException("Invalid microservice access token");

            String microserviceId = microserviceIdExists.get();
            Optional<Microservice> microserviceExists = microserviceRepository.findByIdentifier(microserviceId);

            if (!microserviceExists.isPresent())
                throw new ValidationException(String.format("Microservice with microserviceId: '%s' specified in access token does not exist", microserviceId));

            return microserviceExists.get();
        }
        catch(ValidationException ex) {
            throw ex;
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new SystemErrorException("Error occurred while getting logged in microservice");
        }
    }

    private Optional<String> getMicroserviceIdFromToken(String header) {
        if (StringUtils.isBlank(header)) {
            return Optional.empty();
        }

        String token = header.split(" ")[1];
        String microserviceId = authDecodeService.decodeJwt(token).getMicroserviceId();

        return Optional.ofNullable(microserviceId);
    }

}
