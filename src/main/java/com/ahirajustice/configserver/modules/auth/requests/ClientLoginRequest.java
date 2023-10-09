package com.ahirajustice.configserver.modules.auth.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientLoginRequest {

    @NotBlank(message = "clientId is required")
    private String clientId;
    @NotBlank(message = "clientSecret is required")
    private String clientSecret;
    private int expires;

}
