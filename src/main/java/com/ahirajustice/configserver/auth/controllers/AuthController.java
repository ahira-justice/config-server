package com.ahirajustice.configserver.auth.controllers;

import com.ahirajustice.configserver.auth.requests.ClientLoginRequest;
import com.ahirajustice.configserver.auth.requests.LoginRequest;
import com.ahirajustice.configserver.auth.responses.LoginResponse;
import com.ahirajustice.configserver.auth.services.AuthService;
import com.ahirajustice.configserver.common.error.ErrorResponse;
import com.ahirajustice.configserver.common.error.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Auth")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Client Login")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)) }),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
            }
    )
    @RequestMapping(path = "/client-login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse clientLogin(@Valid @RequestBody ClientLoginRequest request) {
        return authService.clientLogin(request);
    }
}
