package com.ahirajustice.configserver.modules.microservice.requests;

import com.ahirajustice.configserver.modules.microservice.models.RestartConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMicroserviceRequest {

    @NotNull(message = "restartConfig is required")
    private RestartConfig restartConfig;

}
