package com.ahirajustice.configserver.authority.services;

import com.ahirajustice.configserver.authority.viewmodels.AuthorityViewModel;
import com.ahirajustice.configserver.common.entities.Authority;

import java.util.List;

public interface AuthorityService {

    List<AuthorityViewModel> getAuthorities();

    AuthorityViewModel getAuthority(long id);

    Authority verifyAuthorityExists(long id);

}
