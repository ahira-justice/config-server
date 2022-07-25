package com.ahirajustice.configserver.configfetchlog.controllers;

import com.ahirajustice.configserver.common.constants.AuthorityConstants;
import com.ahirajustice.configserver.common.error.ErrorResponse;
import com.ahirajustice.configserver.configfetchlog.queries.SearchConfigFetchLogQuery;
import com.ahirajustice.configserver.configfetchlog.services.ConfigFetchLogService;
import com.ahirajustice.configserver.configfetchlog.viewmodels.ConfigFetchLogViewModel;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.ahirajustice.configserver.common.constants.AuthorityConstants.AUTH_PREFIX;
import static com.ahirajustice.configserver.common.constants.AuthorityConstants.AUTH_SUFFIX;

@Tag(name = "Config")
@RestController
@RequestMapping("api/config-fetch-log")
@RequiredArgsConstructor
public class ConfigFetchLogController {

    private final ConfigFetchLogService configFetchLogService;

    @Operation(summary = "Search Config Fetch Log", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ConfigFetchLogViewModel.class))) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEARCH_CONFIG_FETCH_LOG + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<ConfigFetchLogViewModel> searchConfigs(SearchConfigFetchLogQuery query) {
        return configFetchLogService.searchConfigFetchLog(query);
    }

}
