package com.ahirajustice.configserver.client.controllers;

import com.ahirajustice.configserver.client.queries.SearchClientsQuery;
import com.ahirajustice.configserver.client.requests.CreateClientRequest;
import com.ahirajustice.configserver.client.requests.UpdateClientRequest;
import com.ahirajustice.configserver.client.services.ClientService;
import com.ahirajustice.configserver.client.viewmodels.ClientViewModel;
import com.ahirajustice.configserver.common.constants.AuthorityConstants;
import com.ahirajustice.configserver.common.error.ErrorResponse;
import com.ahirajustice.configserver.common.error.ValidationErrorResponse;
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

import static com.ahirajustice.configserver.common.constants.AuthorityConstants.AUTH_PREFIX;
import static com.ahirajustice.configserver.common.constants.AuthorityConstants.AUTH_SUFFIX;

@Tag(name = "Clients")
@RestController
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Search Clients", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientViewModel.class))) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEARCH_CLIENTS + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientViewModel> searchClients(SearchClientsQuery query) {
        return clientService.searchClients(query);
    }

    @Operation(summary = "Get Client", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ClientViewModel.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_VIEW_CLIENT + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ClientViewModel getClient(@PathVariable long id) {
        return clientService.getClient(id);
    }

    @Operation(summary = "Create Client", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ClientViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_CREATE_CLIENT + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ClientViewModel createClient(@Valid @RequestBody CreateClientRequest request) {
        return clientService.createClient(request);
    }

    @Operation(summary = "Update Client", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ClientViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_UPDATE_CLIENT + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ClientViewModel updateClient(@PathVariable long id, @Valid @RequestBody UpdateClientRequest request) {
        return clientService.updateClient(request, id);
    }

}
