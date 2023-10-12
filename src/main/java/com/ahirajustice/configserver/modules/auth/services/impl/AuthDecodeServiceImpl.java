package com.ahirajustice.configserver.modules.auth.services.impl;

import com.ahirajustice.configserver.common.properties.AppProperties;
import com.ahirajustice.configserver.common.utils.AuthUtils;
import com.ahirajustice.configserver.modules.auth.dtos.AuthToken;
import com.ahirajustice.configserver.modules.auth.services.AuthDecodeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
@RequiredArgsConstructor
public class AuthDecodeServiceImpl implements AuthDecodeService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        PublicKey publicKey = AuthUtils.getPublicKey(appProperties.getPublicKey());

        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
            authToken.setUsername(claims.getSubject());
            authToken.setMicroserviceId(claims.get("microserviceId", String.class));
            authToken.setExpiry(claims.getExpiration());
        }
        catch(ExpiredJwtException | MalformedJwtException ex){
            return authToken;
        }

        return authToken;
    }

    private boolean incorrectPassword(String password, String encryptedPassword) {
        return !passwordEncoder.matches(password, encryptedPassword);
    }




}
