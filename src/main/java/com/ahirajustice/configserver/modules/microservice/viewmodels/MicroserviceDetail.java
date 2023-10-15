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
public class MicroserviceDetail {

    private String identifier;
    private String secretKey;
    private String baseUrl;
    private boolean isActive;

    public static MicroserviceDetail from(Microservice microservice, String decryptedSecretKey) {
        MicroserviceDetail response = new MicroserviceDetail();

        BeanUtils.copyProperties(microservice, response);
        response.setSecretKey(decryptedSecretKey);

        return response;
    }

}
