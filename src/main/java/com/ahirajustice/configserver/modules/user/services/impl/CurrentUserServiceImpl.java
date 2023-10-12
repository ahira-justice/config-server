package com.ahirajustice.configserver.modules.user.services.impl;

import com.ahirajustice.configserver.common.constants.SecurityConstants;
import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.exceptions.SystemErrorException;
import com.ahirajustice.configserver.common.exceptions.ValidationException;
import com.ahirajustice.configserver.common.repositories.UserRepository;
import com.ahirajustice.configserver.modules.auth.services.AuthDecodeService;
import com.ahirajustice.configserver.modules.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentUserServiceImpl implements CurrentUserService {

    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final AuthDecodeService authDecodeService;

    @Override
    public User getCurrentUser() {
        try {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            Optional<String> usernameExists = getUsernameFromToken(header);
            if (usernameExists.isEmpty())
                throw new ValidationException("Invalid user access token");

            String username = usernameExists.get();
            Optional<User> userExists = userRepository.findByUsername(username);

            if (userExists.isEmpty())
                throw new ValidationException(String.format("User with username: '%s' specified in access token does not exist", username));

            return userExists.get();
        }
        catch(ValidationException ex) {
            throw ex;
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new SystemErrorException("Error occurred while getting logged in user");
        }
    }

    private Optional<String> getUsernameFromToken(String header) {
        if (StringUtils.isBlank(header)) {
            return Optional.empty();
        }

        String token = header.split(" ")[1];
        String username = authDecodeService.decodeJwt(token).getUsername();

        return Optional.ofNullable(username);
    }

}
