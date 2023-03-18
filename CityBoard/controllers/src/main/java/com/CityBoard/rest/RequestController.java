package com.CityBoard.rest;

import com.CityBoard.models.JwtAuthentication;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.rest.data.RequestData;
import com.CityBoard.rest.data.RequestStatusData;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.services.AuthService;
import com.CityBoard.services.RequestsService;
import com.CityBoard.services.UsersService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
@OpenAPIDefinition(info = @Info(title = "CityBoard API", version = "v1"))

@Tag(name = "Request API")
@RestController
public class RequestController {
    private final RequestsService requestsService;
    private final UsersService usersService;
    private final AdvertsService advertsService;
    private final AuthService authService;

    public RequestController(RequestsService requestsService, UsersService usersService, AdvertsService advertsService, AuthService authService) {
        this.requestsService = requestsService;
        this.usersService = usersService;
        this.advertsService = advertsService;
        this.authService = authService;
    }


    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully show incoming requests"),
                    @ApiResponse(responseCode = "403", description = "User is unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Server-side problem")},
            description = "Authorization required")
    @GetMapping("/requests")
    public ResponseEntity<List<Requests>> showIncomingRequestsList(@RequestParam(value = "filter", required = false, defaultValue = "")
                                                                   String filter) {
        HttpStatus status = HttpStatus.OK;
        final JwtAuthentication authInfo = authService.getAuthInfo();
        if (authInfo == null) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(status);
        } else {
            Users user = usersService.getUserByUsername(authInfo.getUsername());
            List<Requests> requests;
            if (filter.equals("incoming")) {
                requests = requestsService.getIncomingRequests(user.getId());
            } else if (filter.equals("outgoing")) {
                requests = requestsService.getOutgoingRequests(user.getId());
            } else {
                requests = requestsService.getOutgoingRequests(user.getId());
                requests.addAll(requestsService.getIncomingRequests(user.getId()));
            }
            return new ResponseEntity<>(requests, status);
        }
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "201", description = "Successfully create request"),
                    @ApiResponse(responseCode = "403", description = "User is unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request")},
            description = "Authorization required")
    @PostMapping("/requests")
    public ResponseEntity<Void> makeRequest(@RequestBody RequestData data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/advert/" + data.advertId.toString()));
        final JwtAuthentication authInfo = authService.getAuthInfo();
        if (authInfo == null) {
            return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
        } else if (requestsService.createRequest(usersService.getUserByUsername(authInfo.getUsername()).getId(),
                data.getAdvertId(),
                data.getType())) {
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "204", description = "Successfully change request status"),
                    @ApiResponse(responseCode = "403", description = "User is unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Not found")},
            description = "Authorization required")
    @PatchMapping("/requests/{id}/status")
    public ResponseEntity<Void> changeRequestStatus(@PathVariable("id") Long requestId,
                                                    @RequestBody RequestStatusData data,
                                                    Principal principal) {
        Users user = usersService.getUserByPrincipal(principal);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (requestsService.changeRequestStatus(requestId, data.getStatus())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
