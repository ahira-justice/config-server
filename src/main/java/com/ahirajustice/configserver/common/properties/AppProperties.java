package com.ahirajustice.configserver.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppProperties {

    @Value("${app.config.access-token-expire-minutes}")
    private int accessTokenExpireMinutes;
    @Value("${app.config.public-key}")
    private String publicKey;
    @Value("${app.config.private-key}")
    private String privateKey;
    @Value("${app.config.superuser.password}")
    private String superuserPassword;

}
