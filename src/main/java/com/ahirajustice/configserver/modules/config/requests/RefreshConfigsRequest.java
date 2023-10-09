package com.ahirajustice.configserver.modules.config.requests;

import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
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
public class RefreshConfigsRequest {

    @NotNull(message = "environment is required")
    private ConfigEnvironment environment;

}
