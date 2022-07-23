package com.ahirajustice.configserver.auth.services;

import com.ahirajustice.configserver.auth.dtos.AuthToken;
import com.ahirajustice.configserver.auth.requests.ClientLoginRequest;
import com.ahirajustice.configserver.auth.requests.LoginRequest;
import com.ahirajustice.configserver.auth.responses.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse clientLogin(ClientLoginRequest request);

    AuthToken decodeJwt(String token);

}
