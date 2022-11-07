package com.CityBoard.rest;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.ui.AdminUI;
import com.CityBoard.ui.ClientUI;
import com.CityBoard.ui.ModUI;
import com.CityBoard.ui.NoRegUI;
import com.CityBoard.ui.pagination.Paged;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
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
@SecurityScheme(name = "basicAuth", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER,
        description = "Roles: USER_ROLE, MOD_ROLE, ADMIN_ROLE")
@OpenAPIDefinition(info = @Info(title = "CityBoard API", version = "v1"))

@Tag(name = "Advert API")
@RestController
public class AdvertController {
    private final NoRegUI noRegUI;
    private final ClientUI clientUI;
    private final ModUI modUI;
    private final AdminUI adminUI;

    public AdvertController(NoRegUI noRegUI, ClientUI clientUI, ModUI modUI, AdminUI adminUI) {
        this.noRegUI = noRegUI;
        this.clientUI = clientUI;
        this.modUI = modUI;
        this.adminUI = adminUI;
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", description = "Successfully return adverts page content")},
               description = "Role dependent, no authorization required")
    @GetMapping("/adverts")
    public ResponseEntity<Paged<Adverts>> showAdvertsPaged(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Principal principal) {
        Paged<Adverts> advertsPaged;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user != null) {
            if (user.getRoles().contains(Roles.ROLE_ADMIN)) {
                advertsPaged = adminUI.getAvailableAdvertsPaged(null, currentPage, pageSize);
            }
            else if (user.getRoles().contains(Roles.ROLE_MOD)) {
                advertsPaged = modUI.getAvailableAdvertsPaged(null, currentPage, pageSize);
            }
            else {
                advertsPaged = clientUI.getAvailableAdvertsPaged(user, currentPage, pageSize);
            }
        }
        else {
            advertsPaged = noRegUI.getAvailableAdvertsPaged(null, currentPage, pageSize);
        }
        return new ResponseEntity<>(advertsPaged, HttpStatus.OK);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully return owned adverts list"),
                         @ApiResponse(responseCode = "401", description = "User is unauthorized")},
            description = "Authorization required, ownership checked")
    @GetMapping("/adverts/private")
    public ResponseEntity<List<Adverts>> showUserAdverts(Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(null, status);
        }
        else {
            return new ResponseEntity<>(clientUI.getUserAdverts(user.getId()), status);
        }
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", description = "Successfully return adverts page content"),
                            @ApiResponse(responseCode = "403", description = "Logically forbidden for this role to access"),
                            @ApiResponse(responseCode = "404", description = "Id invalid or advert not found")},
               description = "Role dependent, no authorization required")
    @GetMapping("/advert")
    public ResponseEntity<Adverts> showAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
        Adverts advert;
        HttpStatus status = HttpStatus.OK;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user != null) {
            if (user.getRoles().contains(Roles.ROLE_ADMIN)) {
                advert = adminUI.getAdvert(advertId);
                if (advert == null) {
                    status = HttpStatus.NOT_FOUND;
                }
            }
            else if (user.getRoles().contains(Roles.ROLE_MOD)) {
                advert = modUI.getAdvert(advertId);
                if (advert == null) {
                    status = HttpStatus.NOT_FOUND;
                }
                else if (advert.getStatus().equals(AdvertStatus.DELETED)) {
                    advert = null;
                    status = HttpStatus.FORBIDDEN;
                }
            }
            else {
                advert = clientUI.getAdvert(advertId);
                if (advert == null) {
                    status = HttpStatus.NOT_FOUND;
                }
                else if (!advert.getStatus().equals(AdvertStatus.VISIBLE) && !advert.getAuthorId().equals(user.getId())) {
                    advert = null;
                    status = HttpStatus.FORBIDDEN;
                }
            }
        }
        else {
            advert = noRegUI.getAdvert(advertId);
            if (advert == null) {
                status = HttpStatus.NOT_FOUND;
            }
            else if (!advert.getStatus().equals(AdvertStatus.VISIBLE)) {
                advert = null;
                status = HttpStatus.FORBIDDEN;
            }
        }
        return new ResponseEntity<>(advert, status);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "201", description = "Successfully create new advert",
                    links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                           @LinkParameter(name = "pageSize", expression = "10")})}),
                         @ApiResponse(responseCode = "403", description = "User is unauthrorized",
                    links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                           @LinkParameter(name = "pageSize", expression = "10")})}),
                         @ApiResponse(responseCode = "500", description = "Server-side problem, possibly memory error",
                    links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                           @LinkParameter(name = "pageSize", expression = "10")})})},
            description = "Authorization required")
    @PostMapping("/advert/create")
    public ResponseEntity<Void> createAdvert(@RequestBody Adverts advert, Principal principal) {
        HttpStatus status = HttpStatus.CREATED;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
        }
        else if (!clientUI.createAdvert(advert)) {
            // Can not instantiate a class -- out of memory?
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts?pageNumber=1&pageSize=10"));
        return new ResponseEntity<>(status);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully update existing advert",
                    links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                           @LinkParameter(name = "pageSize", expression = "10")})}),
                    @ApiResponse(responseCode = "403", description = "User is unauthrorized",
                            links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                                    parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                                   @LinkParameter(name = "pageSize", expression = "10")})}),
                    @ApiResponse(responseCode = "400", description = "Id illegally changed",
                            links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                                    parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                                   @LinkParameter(name = "pageSize", expression = "10")})}),
                    @ApiResponse(responseCode = "500", description = "Server-side problem, possibly memory error",
                            links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                                    parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                                   @LinkParameter(name = "pageSize", expression = "10")})})},
            description = "Authorization required")
    @PutMapping("/advert/update")
    public ResponseEntity<Void> updateAdvert(@RequestParam(value = "id") Long advertId,
                                             @RequestBody Adverts advert,
                                             Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.FORBIDDEN;
        }
        else {
            Adverts original = clientUI.getAdvert(advertId);
            if (advert == null || original == null || !original.getId().equals(advertId)) {
                status = HttpStatus.BAD_REQUEST;
            }
            else if (!clientUI.updateAdvert(advert)) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/advert?id" + advertId.toString()));
        return new ResponseEntity<>(headers, status);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully return adverts page content"),
                         @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
            description = "Role required: ROLE_MOD")
    @PatchMapping("/advert/check")
    public ResponseEntity<Void> toggleModCheckOnAdvert(@RequestParam(value = "id") Long advertId) {
        HttpStatus status = HttpStatus.OK;
        if (!modUI.changeAdvertModCheck(advertId)) {
            status = HttpStatus.NOT_FOUND;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/advert?id=" + advertId.toString()));
        return new ResponseEntity<>(headers, status);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully hide advert"),
                    @ApiResponse(responseCode = "403", description = "User unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
            description = "Authorization required, ownership checked")
    @PatchMapping("/advert/hide")
    public ResponseEntity<Void> hideAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
        }
        else {
            Adverts advert = clientUI.getAdvert(advertId);
            if (!user.getId().equals(advert.getAuthorId())) {
                status = HttpStatus.FORBIDDEN;
            }
            else if (!clientUI.hideAdvert(advertId)) {
                status = HttpStatus.NOT_FOUND;
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts/private"));
        return new ResponseEntity<>(headers, status);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully reveal advert"),
                    @ApiResponse(responseCode = "403", description = "User unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
            description = "Authorization required, ownership checked")
    @PatchMapping("/advert/reveal")
    public ResponseEntity<Void> revealAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
        }
        else {
            Adverts advert = clientUI.getAdvert(advertId);
            if (!user.getId().equals(advert.getAuthorId())) {
                status = HttpStatus.FORBIDDEN;
            }
            else if (!clientUI.revealAdvert(advertId)) {
                status = HttpStatus.NOT_FOUND;
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts/private"));
        return new ResponseEntity<>(headers, status);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully delete advert"),
                         @ApiResponse(responseCode = "403", description = "User unauthorized"),
                         @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
                         @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
            description = "Authorization required, ownership checked")
    @DeleteMapping("/advert/delete")
    public ResponseEntity<Void> deleteAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Users user = noRegUI.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
        }
        else {
            Adverts advert = clientUI.getAdvert(advertId);
            if (!user.getId().equals(advert.getAuthorId())) {
                status = HttpStatus.FORBIDDEN;
            }
            else if (!clientUI.deleteAdvert(advertId)) {
                status = HttpStatus.NOT_FOUND;
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts/private"));
        return new ResponseEntity<>(headers, status);
    }
}
