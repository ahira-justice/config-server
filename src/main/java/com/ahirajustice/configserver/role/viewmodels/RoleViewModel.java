package com.ahirajustice.configserver.role.viewmodels;

import com.ahirajustice.configserver.authority.viewmodels.AuthorityViewModel;
import com.ahirajustice.configserver.common.entities.Role;
import com.ahirajustice.configserver.common.viewmodels.BaseViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleViewModel extends BaseViewModel {

    private String name;
    private boolean isSystem;
    private List<AuthorityViewModel> authorities;

    public static RoleViewModel from(Role role) {
        RoleViewModel response = new RoleViewModel();

        BeanUtils.copyProperties(role, response);
        response.setAuthorities(
                role.getAuthorities().stream()
                        .map(AuthorityViewModel::from).collect(Collectors.toList())
        );

        return response;
    }
}
