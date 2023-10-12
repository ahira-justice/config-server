package com.ahirajustice.configserver.modules.configfetchlog.viewmodels;

import com.ahirajustice.configserver.common.entities.ConfigFetchLog;
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
public class ConfigFetchLogViewModel extends BaseViewModel {

    private String retrievedConfig;
    private String microservice;

    public static ConfigFetchLogViewModel from(ConfigFetchLog config) {
        ConfigFetchLogViewModel response = new ConfigFetchLogViewModel();

        BeanUtils.copyProperties(config, response);
        response.setMicroservice(config.getMicroservice().getIdentifier());

        return response;
    }

}
