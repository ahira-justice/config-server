package com.ahirajustice.configserver.common.constants;

public class AuthorityConstants {

    public static final String AUTH_PREFIX = "hasAuthority('";
    public static final String AUTH_SUFFIX = "')";

    // User authorities
    public static final String CAN_VIEW_USER = "CAN_VIEW_USER";
    public static final String CAN_SEARCH_USERS = "CAN_SEARCH_USERS";
    public static final String CAN_CREATE_SUPER_ADMIN = "CAN_CREATE_SUPER_ADMIN";
    public static final String CAN_UPDATE_USER = "CAN_UPDATE_USER";

    // Config authorities
    public static final String CAN_SEARCH_CONFIGS = "CAN_SEARCH_CONFIGS";

    // ConfigFetchLog authorities
    public static final String CAN_SEARCH_CONFIG_FETCH_LOG = "CAN_SEARCH_CONFIG_FETCH_LOG";

    // Client authorities
    public static final String CAN_VIEW_CLIENT = "CAN_VIEW_CLIENT";
    public static final String CAN_SEARCH_CLIENTS = "CAN_SEARCH_CLIENTS";
    public static final String CAN_CREATE_CLIENT = "CAN_CREATE_CLIENT";
    public static final String CAN_UPDATE_CLIENT = "CAN_UPDATE_CLIENT";

    // Authority authorities
    public static final String CAN_VIEW_AUTHORITY = "CAN_VIEW_AUTHORITY";
    public static final String CAN_VIEW_ALL_AUTHORITIES = "CAN_VIEW_ALL_AUTHORITIES";

    // Role authorities
    public static final String CAN_VIEW_ROLE = "CAN_VIEW_ROLE";
    public static final String CAN_VIEW_ALL_ROLES = "CAN_VIEW_ALL_ROLES";
    public static final String CAN_CREATE_ROLE = "CAN_CREATE_ROLE";
    public static final String CAN_UPDATE_ROLE = "CAN_UPDATE_ROLE";

    // Administrative authorities
    public static final String CAN_PERFORM_CLIENT_OPERATIONS = "CAN_PERFORM_CLIENT_OPERATIONS";

}
