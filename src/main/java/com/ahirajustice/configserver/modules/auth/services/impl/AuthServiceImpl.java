package com.ahirajustice.configserver.modules.auth.services.impl;

import com.ahirajustice.configserver.modules.auth.dtos.AuthToken;
import com.ahirajustice.configserver.modules.auth.requests.ClientLoginRequest;
import com.ahirajustice.configserver.modules.auth.requests.LoginRequest;
import com.ahirajustice.configserver.modules.auth.responses.LoginResponse;
import com.ahirajustice.configserver.modules.auth.services.AuthService;
import com.ahirajustice.configserver.common.constants.AuthorityConstants;
import com.ahirajustice.configserver.common.constants.SecurityConstants;
import com.ahirajustice.configserver.common.entities.Authority;
import com.ahirajustice.configserver.common.entities.Client;
import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.enums.TimeFactor;
import com.ahirajustice.configserver.common.exceptions.UnauthorizedException;
import com.ahirajustice.configserver.common.properties.AppProperties;
import com.ahirajustice.configserver.common.repositories.ClientRepository;
import com.ahirajustice.configserver.common.repositories.UserRepository;
import com.ahirajustice.configserver.common.utils.AuthUtils;
import com.ahirajustice.configserver.common.utils.CommonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
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

        if (!userExists.isPresent()) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        if (!verifyPassword(password, userExists.get().getPassword())) {
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
                .claim("roles", Collections.singletonList(user.getRole().getName()))
                .claim("authorities", user.getRole().getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

    @Override
    public LoginResponse clientLogin(ClientLoginRequest request) {
        Client client = authenticateClient(request.getClientId(), request.getClientSecret());
        return generateAuthToken(client, request.getExpires());
    }

    private Client authenticateClient(String clientId, String clientSecret) {
        Optional<Client> clientExists = clientRepository.findByIdentifier(clientId);

        if (!clientExists.isPresent()) {
            throw new UnauthorizedException("Incorrect client credentials");
        }

        if (!verifyPassword(clientSecret, clientExists.get().getSecret())) {
            throw new UnauthorizedException("Incorrect client credentials");
        }

        return clientExists.get();
    }

    private LoginResponse generateAuthToken(Client client, int expires) {
        int expiry = expires > 0 ? expires : appProperties.getAccessTokenExpireMinutes();

        PrivateKey privateKey = AuthUtils.getPrivateKey(appProperties.getPrivateKey());

        String token = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + CommonUtils.convertToMillis(expiry, TimeFactor.MINUTE)))
                .claim("clientId", client.getIdentifier())
                .claim("authorities", Collections.singletonList(AuthorityConstants.CAN_PERFORM_CLIENT_OPERATIONS))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        PublicKey publicKey = AuthUtils.getPublicKey(appProperties.getPublicKey());

        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
            authToken.setUsername(claims.getSubject());
            authToken.setClientId(claims.get("clientId", String.class));
            authToken.setExpiry(claims.getExpiration());
            authToken.setRoles(claims.get("roles", ArrayList.class));
            authToken.setAuthorities(claims.get("authorities", ArrayList.class));
        }
        catch(ExpiredJwtException | MalformedJwtException ex){
            return authToken;
        }

        return authToken;
    }

    private boolean verifyPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }




}
