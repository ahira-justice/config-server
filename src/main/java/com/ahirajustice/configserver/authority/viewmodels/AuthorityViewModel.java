package com.ahirajustice.configserver.authority.viewmodels;

import com.ahirajustice.configserver.common.entities.Authority;
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
public class AuthorityViewModel extends BaseViewModel {

    private String name;
    private boolean isSystem;

    public static AuthorityViewModel from(Authority authority) {
        AuthorityViewModel response = new AuthorityViewModel();

        BeanUtils.copyProperties(authority, response);

        return response;
    }

}
