package com.ahirajustice.configserver.user.services;

import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.enums.Roles;
import com.ahirajustice.configserver.user.queries.SearchUsersQuery;
import com.ahirajustice.configserver.user.requests.CreateUserRequest;
import com.ahirajustice.configserver.user.requests.UpdateUserRequest;
import com.ahirajustice.configserver.user.viewmodels.UserViewModel;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);

    UserViewModel getUser(long id);

    UserViewModel createUser(CreateUserRequest request);

    User createUser(CreateUserRequest request, Roles role);

    UserViewModel updateUser(UpdateUserRequest request, long id);

}
