package com.ahirajustice.configserver.modules.user.controllers;

import com.ahirajustice.configserver.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.configserver.modules.user.requests.CreateUserRequest;
import com.ahirajustice.configserver.modules.user.requests.UpdateUserRequest;
import com.ahirajustice.configserver.modules.user.services.UserService;
import com.ahirajustice.configserver.modules.user.viewmodels.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        return userService.searchUsers(query);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewModel createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel updateUser(@PathVariable long id, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(request, id);
    }

}
