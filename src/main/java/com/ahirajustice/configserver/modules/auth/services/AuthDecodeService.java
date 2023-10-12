package com.ahirajustice.configserver.modules.auth.services;

import com.ahirajustice.configserver.modules.auth.dtos.AuthToken;

public interface AuthDecodeService {

    AuthToken decodeJwt(String token);

}
