package com.ahirajustice.configserver.config.responses;

import com.ahirajustice.configserver.common.entities.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEntry {

    private String configKey;
    private String configValue;
    private Boolean encrypted;

    public static ConfigEntry from(Config config) {
        ConfigEntry response = new ConfigEntry();

        response.setConfigKey(config.getConfigKey());
        response.setConfigValue(config.getConfigValue());
        response.setEncrypted(config.getEncrypted());

        return response;
    }

}
