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
public class UpdateMicroserviceRequest {

    @NotBlank(message = "baseUrl is required")
    private String baseUrl;

}
