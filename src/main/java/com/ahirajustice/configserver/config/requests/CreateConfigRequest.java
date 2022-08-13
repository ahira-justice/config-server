package com.ahirajustice.configserver.config.requests;

import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConfigRequest {

    @NotBlank(message = "key is required")
    private String key;
    @NotBlank(message = "value is required")
    private String value;
    @NotNull(message = "configEnvironment is required")
    private ConfigEnvironment configEnvironment;
    @Getter(AccessLevel.NONE)
    private boolean encrypt;

    public boolean encrypt(){
        return this.encrypt;
    }

}
