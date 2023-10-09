package com.ahirajustice.configserver.modules.config.controllers;

import com.ahirajustice.configserver.common.constants.AuthorityConstants;
import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
import com.ahirajustice.configserver.common.error.ErrorResponse;
import com.ahirajustice.configserver.common.error.ValidationErrorResponse;
import com.ahirajustice.configserver.common.responses.SimpleMessageResponse;
import com.ahirajustice.configserver.modules.config.queries.SearchConfigsQuery;
import com.ahirajustice.configserver.modules.config.requests.BatchCreateConfigsRequest;
import com.ahirajustice.configserver.modules.config.requests.CreateConfigRequest;
import com.ahirajustice.configserver.modules.config.requests.RefreshConfigsRequest;
import com.ahirajustice.configserver.modules.config.services.ConfigService;
import com.ahirajustice.configserver.modules.config.responses.ConfigEntry;
import com.ahirajustice.configserver.modules.config.viewmodels.ConfigViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static com.ahirajustice.configserver.common.constants.AuthorityConstants.AUTH_PREFIX;
import static com.ahirajustice.configserver.common.constants.AuthorityConstants.AUTH_SUFFIX;

@Tag(name = "Config")
@RestController
@RequestMapping("api/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @Operation(summary = "Fetch Configs", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ConfigViewModel.class))) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_PERFORM_CLIENT_OPERATIONS + AUTH_SUFFIX)
    @RequestMapping(path = "/{environment}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ConfigEntry> fetchConfigs(@PathVariable ConfigEnvironment environment) {
        return configService.fetchConfigs(environment);
    }

    @Operation(summary = "Search Configs", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ConfigViewModel.class))) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEARCH_CONFIGS + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<ConfigViewModel> searchConfigs(SearchConfigsQuery query) {
        return configService.searchConfigs(query);
    }

    @Operation(summary = "Create Config", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_PERFORM_CLIENT_OPERATIONS + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createConfig(@Valid @RequestBody CreateConfigRequest request) {
        configService.createConfig(request);
    }

    @Operation(summary = "Batch Create Configs", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_PERFORM_CLIENT_OPERATIONS + AUTH_SUFFIX)
    @RequestMapping(path = "/batch", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void batchCreateConfigs(@Valid @RequestBody BatchCreateConfigsRequest request) {
        configService.batchCreateConfigs(request);
    }

    @Operation(summary = "Refresh Configs", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
            }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_PERFORM_CLIENT_OPERATIONS + AUTH_SUFFIX)
    @RequestMapping(path = "/refresh", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public SimpleMessageResponse refreshConfigs(@Valid @RequestBody RefreshConfigsRequest request) {
        return configService.refreshConfigs(request);
    }

}
