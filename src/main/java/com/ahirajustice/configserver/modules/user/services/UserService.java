package com.ahirajustice.configserver.modules.user.services;

import com.ahirajustice.configserver.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.configserver.modules.user.requests.CreateUserRequest;
import com.ahirajustice.configserver.modules.user.requests.UpdateUserRequest;
import com.ahirajustice.configserver.modules.user.viewmodels.UserViewModel;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);
    UserViewModel getUser(long id);
    UserViewModel createUser(CreateUserRequest request);
    UserViewModel updateUser(UpdateUserRequest request, long id);

}
