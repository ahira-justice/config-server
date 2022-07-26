package com.ahirajustice.configserver.user.services;


import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.enums.Roles;

public interface CurrentUserService {
    
    User getCurrentUser();

    boolean currentUserHasRole(Roles role);

}
