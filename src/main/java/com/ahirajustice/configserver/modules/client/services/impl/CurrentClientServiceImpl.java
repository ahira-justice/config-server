package com.ahirajustice.configserver.modules.client.services.impl;

import com.ahirajustice.configserver.modules.auth.services.AuthService;
import com.ahirajustice.configserver.modules.client.services.CurrentClientService;
import com.ahirajustice.configserver.common.constants.SecurityConstants;
import com.ahirajustice.configserver.common.entities.Client;
import com.ahirajustice.configserver.common.exceptions.SystemErrorException;
import com.ahirajustice.configserver.common.exceptions.ValidationException;
import com.ahirajustice.configserver.common.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentClientServiceImpl implements CurrentClientService {

    private final HttpServletRequest request;
    private final ClientRepository clientRepository;
    private final AuthService authService;

    @Override
    public Client getCurrentClient() {
        try {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            Optional<String> clientIdExists = getClientIdFromToken(header);
            if (!clientIdExists.isPresent())
                throw new ValidationException("Invalid client access token");

            String clientId = clientIdExists.get();
            Optional<Client> clientExists = clientRepository.findByIdentifier(clientId);

            if (!clientExists.isPresent())
                throw new ValidationException(String.format("Client with clientId: '%s' specified in access token does not exist", clientId));

            return clientExists.get();
        }
        catch(ValidationException ex) {
            throw ex;
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new SystemErrorException("Error occurred while getting logged in client");
        }
    }

    private Optional<String> getClientIdFromToken(String header) {
        if (StringUtils.isBlank(header)) {
            return Optional.empty();
        }

        String token = header.split(" ")[1];
        String clientId = authService.decodeJwt(token).getClientId();

        return Optional.ofNullable(clientId);
    }

}
