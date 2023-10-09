package com.ahirajustice.configserver.modules.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

    private String username;
    private String clientId;
    private Date expiry;
    private List<String> roles;
    private List<String> authorities;

}
