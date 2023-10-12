package com.ahirajustice.configserver.modules.auth.services;

import com.ahirajustice.configserver.modules.auth.requests.LoginRequest;
import com.ahirajustice.configserver.modules.auth.responses.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}
