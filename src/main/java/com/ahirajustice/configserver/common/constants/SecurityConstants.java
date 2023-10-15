package com.ahirajustice.configserver.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String[] EXCLUDE_FROM_AUTH_URLS = new String[] {
            "/**, OPTIONS",
            "/, GET",
            "/api/auth/token, POST",
    };
    public static final String[] EXCLUDE_FROM_REQUEST_RESPONSE_LOGGER = new String[] {
            "/**, OPTIONS",
            "/, GET",
            "/api/auth/token, POST",
            "/api/users, POST",
    };

}
