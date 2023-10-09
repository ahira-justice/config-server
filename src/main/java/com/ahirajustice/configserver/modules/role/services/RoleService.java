package com.ahirajustice.configserver.modules.role.services;

import com.ahirajustice.configserver.modules.role.requests.CreateRoleRequest;
import com.ahirajustice.configserver.modules.role.requests.UpdateRoleRequest;
import com.ahirajustice.configserver.modules.role.viewmodels.RoleViewModel;

import java.util.List;

public interface RoleService {

    List<RoleViewModel> getRoles();

    RoleViewModel getRole(long id);

    RoleViewModel createRole(CreateRoleRequest request);

    RoleViewModel updateRole(UpdateRoleRequest request, long id);

}
