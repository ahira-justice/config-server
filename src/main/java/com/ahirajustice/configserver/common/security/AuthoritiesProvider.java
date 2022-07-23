package com.ahirajustice.configserver.common.security;

import com.ahirajustice.configserver.common.constants.AuthorityConstants;
import com.ahirajustice.configserver.common.entities.Authority;
import com.ahirajustice.configserver.common.entities.Role;
import com.ahirajustice.configserver.common.enums.Roles;

import java.util.HashSet;
import java.util.Set;

public class AuthoritiesProvider {

    // User authorities
    public static Authority CAN_VIEW_USER = new Authority(AuthorityConstants.CAN_VIEW_USER);
    public static Authority CAN_SEARCH_USERS = new Authority(AuthorityConstants.CAN_SEARCH_USERS, true);
    public static Authority CAN_CREATE_SUPER_ADMIN = new Authority(AuthorityConstants.CAN_CREATE_SUPER_ADMIN, true);
    public static Authority CAN_UPDATE_USER = new Authority(AuthorityConstants.CAN_UPDATE_USER);

    // Config authorities
    public static Authority CAN_SEARCH_CONFIGS = new Authority(AuthorityConstants.CAN_SEARCH_CONFIGS);

    // Client authorities
    public static Authority CAN_VIEW_CLIENT = new Authority(AuthorityConstants.CAN_VIEW_CLIENT, true);
    public static Authority CAN_SEARCH_CLIENTS = new Authority(AuthorityConstants.CAN_SEARCH_CLIENTS, true);
    public static Authority CAN_CREATE_CLIENT = new Authority(AuthorityConstants.CAN_CREATE_CLIENT, true);
    public static Authority CAN_UPDATE_CLIENT = new Authority(AuthorityConstants.CAN_UPDATE_CLIENT, true);

    // Authority authorities
    public static Authority CAN_VIEW_AUTHORITY = new Authority(AuthorityConstants.CAN_VIEW_AUTHORITY, true);
    public static Authority CAN_VIEW_ALL_AUTHORITIES = new Authority(AuthorityConstants.CAN_VIEW_ALL_AUTHORITIES, true);

    // Role authorities
    public static Authority CAN_VIEW_ROLE = new Authority(AuthorityConstants.CAN_VIEW_ROLE, true);
    public static Authority CAN_VIEW_ALL_ROLES = new Authority(AuthorityConstants.CAN_VIEW_ALL_ROLES, true);
    public static Authority CAN_CREATE_ROLE = new Authority(AuthorityConstants.CAN_CREATE_ROLE, true);
    public static Authority CAN_UPDATE_ROLE = new Authority(AuthorityConstants.CAN_UPDATE_ROLE, true);

    public static Set<Authority> getAllAuthorities() {
        Set<Authority> authorities = new HashSet<>();

        // User authorities
        authorities.add(CAN_VIEW_USER);
        authorities.add(CAN_SEARCH_USERS);
        authorities.add(CAN_CREATE_SUPER_ADMIN);
        authorities.add(CAN_UPDATE_USER);

        // Config authorities
        authorities.add(CAN_SEARCH_CONFIGS);

        // Client authorities
        authorities.add(CAN_VIEW_CLIENT);
        authorities.add(CAN_SEARCH_CLIENTS);
        authorities.add(CAN_CREATE_CLIENT);
        authorities.add(CAN_UPDATE_CLIENT);

        // Authority authorities
        authorities.add(CAN_VIEW_AUTHORITY);
        authorities.add(CAN_VIEW_ALL_AUTHORITIES);

        // Role authorities
        authorities.add(CAN_VIEW_ROLE);
        authorities.add(CAN_VIEW_ALL_ROLES);
        authorities.add(CAN_CREATE_ROLE);
        authorities.add(CAN_UPDATE_ROLE);

        return authorities;
    }

    private static Set<Authority> getUserAuthorities() {
        Set<Authority> authorities = new HashSet<>();

        // User authorities
        authorities.add(CAN_VIEW_USER);
        authorities.add(CAN_UPDATE_USER);

        return authorities;
    }

    private static Set<Authority> getSuperAdminAuthorities() {
        return getAllAuthorities();
    }

    public static Set<Role> getDefaultRoles() {
        Set<Role> roles = new HashSet<>();

        Role user = new Role(Roles.USER.name());
        user.setAuthorities(getUserAuthorities());
        roles.add(user);

        Role superAdmin = new Role(Roles.SUPERADMIN.name(), true);
        superAdmin.setAuthorities(getSuperAdminAuthorities());
        roles.add(superAdmin);

        return roles;
    }

}
