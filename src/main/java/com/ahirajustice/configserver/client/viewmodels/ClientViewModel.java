package com.ahirajustice.configserver.client.viewmodels;

import com.ahirajustice.configserver.common.entities.Client;
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
public class ClientViewModel {

    private String identifier;
    private String name;
    private String adminEmail;
    private String restartCallbackUrl;
    private boolean isActive;

    public static ClientViewModel from(Client client) {
        ClientViewModel response = new ClientViewModel();

        BeanUtils.copyProperties(client, response);

        return response;
    }

}
