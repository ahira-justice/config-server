package com.ahirajustice.configserver.modules.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

    private String username;
    private String microserviceId;
    private Date expiry;

}
