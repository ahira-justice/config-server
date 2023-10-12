package com.ahirajustice.configserver.modules.microservice.viewmodels;

import com.ahirajustice.configserver.common.entities.Microservice;
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
public class MicroserviceViewModel {

    private String identifier;
    private String name;
    private String refreshCallbackUrl;
    private boolean isActive;

    public static MicroserviceViewModel from(Microservice microservice) {
        MicroserviceViewModel response = new MicroserviceViewModel();

        BeanUtils.copyProperties(microservice, response);

        return response;
    }

}
