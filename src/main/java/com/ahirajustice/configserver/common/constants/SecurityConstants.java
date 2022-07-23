package com.ahirajustice.configserver.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    // Auth
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    // URLs
    public static final String[] EXCLUDE_FROM_AUTH_URLS = new String[] {
            "/**, OPTIONS",
            "/, GET",
            "/api/auth/login, POST",
            "/api/users, POST",
            "/api/config-server/docs, GET",
            "/api/config-server/docs.yaml, GET",
            "/api/config-server/**, GET"
    };

    public static final String[] EXCLUDE_FROM_REQUEST_RESPONSE_LOGGER = new String[] {
            "/**, OPTIONS",
            "/, GET",
            "/api/auth/login, POST",
            "/api/users, POST",
            "/api/config-server/docs, GET",
            "/api/config-server/docs.yaml, GET",
            "/api/config-server/**, GET"
    };

}
