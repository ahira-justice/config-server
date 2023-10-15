package com.ahirajustice.configserver.modules.microservice.requests;

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
public class CreateMicroserviceRequest {

    @NotBlank(message = "identifier is required")
    private String identifier;
    @NotBlank(message = "baseUrl is required")
    private String baseUrl;
    @NotBlank(message = "encryptingKey is required")
    private String encryptingKey;

}
