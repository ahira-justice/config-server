package com.ahirajustice.configserver.modules.microservice.viewmodels;

import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.common.viewmodels.BaseViewModel;
import com.ahirajustice.configserver.modules.microservice.models.RestartConfig;
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
public class MicroserviceViewModel extends BaseViewModel {

    private String identifier;
    private RestartConfig restartConfig;
    private boolean isActive;

    public static MicroserviceViewModel from(Microservice microservice) {
        MicroserviceViewModel response = new MicroserviceViewModel();

        BeanUtils.copyProperties(microservice, response);

        return response;
    }

}
