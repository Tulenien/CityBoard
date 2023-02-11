package com.CityBoard.rest;

import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dbmodels.UsersPostgres;
import com.CityBoard.ui.ClientUI;
import com.CityBoard.ui.NoRegUI;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
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
@SecurityScheme(name = "basicAuth", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER,
        description = "Roles: USER_ROLE, MOD_ROLE, ADMIN_ROLE")
@OpenAPIDefinition(info = @Info(title = "CityBoard API", version = "v1"))

@Tag(name = "Request API")
@SecurityRequirement(name = "basicAuth")
@RestController
public class RequestController {
    private final ClientUI clientUI;
    private final NoRegUI noRegUI;

    public RequestController(ClientUI clientUI, NoRegUI noRegUI) {
        this.clientUI = clientUI;
        this.noRegUI = noRegUI;
    }

    @GetMapping("/requests/incoming")
    public ResponseEntity<List<Requests>> showIncomingRequestsList(@RequestParam("id") Long userId) {
        List<Requests> requests = clientUI.getIncomingRequests(userId);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/requests/outgoing")
    public ResponseEntity<List<Requests>> showOutgoingRequestsList(@RequestParam("id") Long userId) {
        List<Requests> requests = clientUI.getOutgoingRequests(userId);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PutMapping("/request/accept")
    public ResponseEntity<Void> acceptRequest(@RequestParam("id") Long requestId, Principal principal) {
        Users user = noRegUI.getUserByPrincipal(principal);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/requests/incoming?id=" + user.getId().toString()));
        if (clientUI.acceptRequest(requestId)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/request/reject")
    public ResponseEntity<Void> rejectRequest(@RequestParam("id") Long requestId, Principal principal) {
        Users user = noRegUI.getUserByPrincipal(principal);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/requests/incoming?id=" + user.getId().toString()));
        if (clientUI.acceptRequest(requestId)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/request/create")
    public ResponseEntity<Void> makeRequest(@RequestParam("id") Long advertId,
                                            @RequestParam("type") RequestType type,
                                            Principal principal) {
        UsersPostgres user = noRegUI.getUserDTOByPrincipal(principal);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/advert?id=" + advertId.toString()));
        if (clientUI.makeRequest(user, advertId, type)) {
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }
}
