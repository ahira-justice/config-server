package com.ahirajustice.configserver.config.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchCreateConfigsRequest {

    @NotEmpty(message = "At least one config must be defined")
    List<@Valid CreateConfigRequest> requests;

}
