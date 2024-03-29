package com.ahirajustice.configserver.modules.config.viewmodels;

import com.ahirajustice.configserver.common.entities.Config;
import com.ahirajustice.configserver.common.viewmodels.BaseViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigViewModel extends BaseViewModel {

    private String configKey;
    private String configValue;
    private Boolean encrypted;
    private String microservice;

    public static ConfigViewModel from(Config config) {
        ConfigViewModel response = new ConfigViewModel();

        BeanUtils.copyProperties(config, response);
        response.setMicroservice(config.getMicroservice().getIdentifier());

        return response;
    }

}
