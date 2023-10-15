package com.ahirajustice.configserver.modules.config.requests;

import lombok.AccessLevel;
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
public class CreateConfigRequest {

    @NotBlank(message = "key is required")
    private String key;
    @NotBlank(message = "value is required")
    private String value;
    @Getter(AccessLevel.NONE)
    @Builder.Default
    private boolean encrypt = true;

    public boolean encrypt(){
        return this.encrypt;
    }

}
