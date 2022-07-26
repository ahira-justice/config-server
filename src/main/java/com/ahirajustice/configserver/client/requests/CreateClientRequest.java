package com.ahirajustice.configserver.client.requests;

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
public class CreateClientRequest {

    @NotBlank(message = "identifier is required")
    private String identifier;
    @NotBlank(message = "secret is required")
    private String secret;
    @NotBlank(message = "adminEmail is required")
    private String adminEmail;
    @NotBlank(message = "restartCallbackUrl is required")
    private String restartCallbackUrl;

}
