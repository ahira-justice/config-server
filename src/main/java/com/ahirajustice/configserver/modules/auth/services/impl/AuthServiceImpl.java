package com.ahirajustice.configserver.modules.auth.services.impl;

import com.ahirajustice.configserver.common.constants.SecurityConstants;
import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.enums.TimeFactor;
import com.ahirajustice.configserver.common.exceptions.UnauthorizedException;
import com.ahirajustice.configserver.common.properties.AppProperties;
import com.ahirajustice.configserver.common.repositories.UserRepository;
import com.ahirajustice.configserver.common.utils.AuthUtils;
import com.ahirajustice.configserver.common.utils.CommonUtils;
import com.ahirajustice.configserver.modules.auth.requests.LoginRequest;
import com.ahirajustice.configserver.modules.auth.responses.LoginResponse;
import com.ahirajustice.configserver.modules.auth.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = authenticateUser(request.getUsername(), request.getPassword());
        return generateAuthToken(user, request.getExpires());
    }

    private User authenticateUser(String username, String password) {
        Optional<User> userExists = userRepository.findByUsername(username);

        if (userExists.isEmpty()) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        if (incorrectPassword(password, userExists.get().getPassword())) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        return userExists.get();
    }

    private LoginResponse generateAuthToken(User user, int expires) {
        int expiry = expires > 0 ? expires : appProperties.getAccessTokenExpireMinutes();

        PrivateKey privateKey = AuthUtils.getPrivateKey(appProperties.getPrivateKey());

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + CommonUtils.convertToMillis(expiry, TimeFactor.MINUTE)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

    private boolean incorrectPassword(String password, String encryptedPassword) {
        return !passwordEncoder.matches(password, encryptedPassword);
    }

}
