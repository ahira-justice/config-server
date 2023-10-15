package com.ahirajustice.configserver.modules.microservice.services.impl;

import com.ahirajustice.configserver.common.constants.SecurityConstants;
import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.common.exceptions.SystemErrorException;
import com.ahirajustice.configserver.common.exceptions.ValidationException;
import com.ahirajustice.configserver.common.repositories.MicroserviceRepository;
import com.ahirajustice.configserver.common.utils.AuthUtils;
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

    @Override
    public Microservice getCurrentMicroservice() {
        try {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            String microserviceSecretKey = getMicroserviceSecretKeyFromToken(header).orElse(null);
            if (microserviceSecretKey == null)
                throw new ValidationException("Invalid microservice secret key");

            Microservice microservice = microserviceRepository.findByHashedSecretKey(
                    AuthUtils.getSha256Hash(microserviceSecretKey)
            ).orElse(null);

            if (microservice == null)
                throw new ValidationException("Invalid microservice secret key");

            return microservice;
        }
        catch(ValidationException ex) {
            throw ex;
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new SystemErrorException("Error occurred while getting logged in microservice");
        }
    }

    private Optional<String> getMicroserviceSecretKeyFromToken(String header) {
        if (StringUtils.isBlank(header))
            return Optional.empty();

        String token = header.split(" ")[1];

        return Optional.ofNullable(token);
    }

}
