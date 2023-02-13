package com.CityBoard.rest;

import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.rest.data.RequestData;
import com.CityBoard.rest.data.RequestStatusData;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.services.RequestsService;
import com.CityBoard.services.UsersService;
import com.CityBoard.ui.ClientUI;
import com.CityBoard.ui.NoRegUI;
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
@SecurityRequirement(name = "basicAuth")
@RestController
public class RequestController {
    private final RequestsService requestsService;
    private final UsersService usersService;
    private final AdvertsService advertsService;

    public RequestController(RequestsService requestsService, UsersService usersService, AdvertsService advertsService) {
        this.requestsService = requestsService;
        this.usersService = usersService;
        this.advertsService = advertsService;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully show incoming requests"),
                         @ApiResponse(responseCode = "403", description = "User is unauthorized"),
                         @ApiResponse(responseCode = "500", description = "Server-side problem")},
            description = "Authorization required")
    @GetMapping("/requests/incoming")
    public ResponseEntity<List<Requests>> showIncomingRequestsList(Principal principal) {
        Users user = usersService.getUserByPrincipal(principal);
        HttpStatus status = HttpStatus.OK;
        if (user == null) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(status);
        }
        else {
            List<Requests> requests = requestsService.getIncomingRequests(user.getId());
            return new ResponseEntity<>(requests, status);
        }
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully show outgoing requests"),
                    @ApiResponse(responseCode = "403", description = "User is unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Server-side problem")},
            description = "Authorization required")
    @GetMapping("/requests/outgoing")
    public ResponseEntity<List<Requests>> showOutgoingRequestsList(Principal principal) {
        Users user = usersService.getUserByPrincipal(principal);
        HttpStatus status = HttpStatus.OK;
        if (user == null) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(status);
        }
        else {
            List<Requests> requests = requestsService.getOutgoingRequests(user.getId());
            return new ResponseEntity<>(requests, status);
        }
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "201", description = "Successfully create request"),
                    @ApiResponse(responseCode = "403", description = "User is unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad request")},
            description = "Authorization required")
    @PostMapping("/requests")
    public ResponseEntity<Void> makeRequest(@RequestBody RequestData data,
                                            Principal principal) {
        Users user = usersService.getUserByPrincipal(principal);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/advert/" + data.advertId.toString()));
        if (user == null) {
            return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
        }
        else if (requestsService.createRequest(usersService.getUserDTOById(user.getId()),
                                          advertsService.getAdvertDTOById(data.getAdvertId()),
                                          data.getType()) != null) {
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

    //@PutMapping("/request/accept")
    //public ResponseEntity<Void> acceptRequest(@RequestParam("id") Long requestId, Principal principal) {
    //    Users user = noRegUI.getUserByPrincipal(principal);
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setLocation(URI.create("/requests/incoming?id=" + user.getId().toString()));
    //    if (clientUI.acceptRequest(requestId)) {
    //        return new ResponseEntity<>(headers, HttpStatus.OK);
    //    }
    //    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //}
//
    //@PutMapping("/request/reject")
    //public ResponseEntity<Void> rejectRequest(@RequestParam("id") Long requestId, Principal principal) {
    //    Users user = noRegUI.getUserByPrincipal(principal);
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setLocation(URI.create("/requests/incoming?id=" + user.getId().toString()));
    //    if (clientUI.acceptRequest(requestId)) {
    //        return new ResponseEntity<>(headers, HttpStatus.OK);
    //    }
    //    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //}
}
