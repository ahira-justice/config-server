package com.ahirajustice.configserver.modules.auth.controllers;

import com.ahirajustice.configserver.modules.auth.requests.LoginRequest;
import com.ahirajustice.configserver.modules.auth.responses.LoginResponse;
import com.ahirajustice.configserver.modules.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @RequestMapping(path = "/token", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
