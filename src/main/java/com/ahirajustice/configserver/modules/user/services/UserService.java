package com.ahirajustice.configserver.modules.user.services;

import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.enums.Roles;
import com.ahirajustice.configserver.modules.user.viewmodels.UserViewModel;
import com.ahirajustice.configserver.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.configserver.modules.user.requests.CreateUserRequest;
import com.ahirajustice.configserver.modules.user.requests.UpdateUserRequest;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);

    UserViewModel getUser(long id);

    UserViewModel createUser(CreateUserRequest request);

    User createUser(CreateUserRequest request, Roles role);

    UserViewModel updateUser(UpdateUserRequest request, long id);

}
