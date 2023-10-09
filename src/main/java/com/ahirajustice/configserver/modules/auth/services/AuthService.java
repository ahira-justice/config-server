package com.ahirajustice.configserver.modules.auth.services;

import com.ahirajustice.configserver.modules.auth.dtos.AuthToken;
import com.ahirajustice.configserver.modules.auth.requests.ClientLoginRequest;
import com.ahirajustice.configserver.modules.auth.requests.LoginRequest;
import com.ahirajustice.configserver.modules.auth.responses.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse clientLogin(ClientLoginRequest request);

    AuthToken decodeJwt(String token);

}
