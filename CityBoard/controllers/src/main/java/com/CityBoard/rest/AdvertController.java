package com.CityBoard.rest;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.JwtAuthentication;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.rest.data.AdvertFormData;
import com.CityBoard.services.*;
import com.CityBoard.ui.pagination.Paged;
import com.CityBoard.ui.pagination.Paging;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
@OpenAPIDefinition(info = @Info(title = "CityBoard API", version = "v1"))

@Tag(name = "Advert API")
@RestController
public class AdvertController {
    private final AdvertsService advertsService;
    private final UsersService usersService;
    private final RequestsService requestsService;
    private final AuthService authService;

    public AdvertController(AdvertsService advertsService, UsersService usersService, RequestsService requestsService,
                            AuthService authService) {
        this.advertsService = advertsService;
        this.usersService = usersService;
        this.requestsService = requestsService;
        this.authService = authService;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
               responses = {
            @ApiResponse(responseCode = "200", description = "Successfully return adverts page content")},
            description = "Role dependent, no authorization required")
    @GetMapping("/adverts")
    public ResponseEntity<Paged<Adverts>> showAdvertsPaged(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Paged<Adverts> advertsPaged;
        final JwtAuthentication authInfo = authService.getAuthInfo();
        if (authInfo != null) {
            if (authInfo.getRoles().contains(Roles.ROLE_ADMIN)) {
                Page<Adverts> advertsPage = advertsService.getAllAdvertsPage(currentPage, pageSize);
                advertsPaged = new Paged<>(advertsPage, Paging.of(advertsPage.getTotalPages(), currentPage, pageSize));
            }
            else if (authInfo.getRoles().contains(Roles.ROLE_MOD)) {
                Page<Adverts> advertsPage = advertsService.getNotDeletedAdvertsPage(currentPage, pageSize);
                advertsPaged = new Paged<>(advertsPage, Paging.of(advertsPage.getTotalPages(), currentPage, pageSize));
            }
            else {
                Page<Adverts> advertsPage = advertsService.getVisibleAdvertsPage(currentPage, pageSize);
                advertsPaged = new Paged<>(advertsPage, Paging.of(advertsPage.getTotalPages(), currentPage, pageSize));
            }
        }
        else {
            Page<Adverts> advertsPage = advertsService.getVisibleAdvertsPage(currentPage, pageSize);
            advertsPaged = new Paged<>(advertsPage, Paging.of(advertsPage.getTotalPages(), currentPage, pageSize));
        }
        return new ResponseEntity<>(advertsPaged, HttpStatus.OK);
    }


    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(
                        responseCode = "201", description = "Successfully create new advert",
                        links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                    @LinkParameter(name = "pageSize", expression = "10")})}),
                    @ApiResponse(responseCode = "403", description = "User is unauthorized",
                            links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                                    parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                            @LinkParameter(name = "pageSize", expression = "10")})}),
                    @ApiResponse(responseCode = "500", description = "Server-side problem",
                            links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
                                    parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                            @LinkParameter(name = "pageSize", expression = "10")})})},
            description = "Authorization required")
    @PostMapping("/adverts")
    public ResponseEntity<Void> createAdvert(@RequestBody AdvertFormData advert) {
        HttpStatus status = HttpStatus.CREATED;
        HttpHeaders headers = new HttpHeaders();
        final JwtAuthentication authInfo = authService.getAuthInfo();
        if (authInfo == null) {
            status = HttpStatus.UNAUTHORIZED;
            headers.setLocation(URI.create("/login"));
        }
        else {
            Users user = usersService.getUserByUsername(authInfo.getUsername());
            Adverts mapped = advert.mapToAdverts();
            advertsService.createAdvert(mapped, user.getId());
            headers.setLocation(URI.create("/adverts?pageNumber=1&pageSize=10"));
        }
        return new ResponseEntity<>(status);
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", description = "Successfully return adverts page content"),
                            @ApiResponse(responseCode = "403", description = "Logically forbidden for this role to access"),
                            @ApiResponse(responseCode = "404", description = "Id invalid or advert not found")},
               description = "Role dependent, no authorization required")
    @GetMapping("/adverts/{id}")
    public ResponseEntity<Adverts> showAdvert(@PathVariable("id") Long advertId) {
        //Adverts advert = null;
        HttpStatus status = HttpStatus.OK;
        Adverts advert = advertsService.getAdvertById(advertId);
        return new ResponseEntity<>(advert, status);
    }

    //@Operation(security = @SecurityRequirement(name = "basicAuth"),
    //        responses = {@ApiResponse(responseCode = "201", description = "Successfully create new advert",
    //                links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
    //                        parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
    //                                       @LinkParameter(name = "pageSize", expression = "10")})}),
    //                     @ApiResponse(responseCode = "403", description = "User is unauthrorized",
    //                links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
    //                        parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
    //                                       @LinkParameter(name = "pageSize", expression = "10")})}),
    //                     @ApiResponse(responseCode = "500", description = "Server-side problem, possibly memory error",
    //                links = {@Link(name = "Show adverts paged", operationId = "showAdvertsPaged",
    //                        parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
    //                                       @LinkParameter(name = "pageSize", expression = "10")})})},
    //        description = "Authorization required")
    //@PostMapping("/advert/create")
    //public ResponseEntity<Void> createAdvert(@RequestBody Adverts advert, Principal principal) {
    //    HttpStatus status = HttpStatus.CREATED;
    //    Users user = noRegUI.getUserByPrincipal(principal);
    //    if (user == null) {
    //        status = HttpStatus.UNAUTHORIZED;
    //    }
    //    else if (!clientUI.createAdvert(advert)) {
    //        // Can not instantiate a class -- out of memory?
    //        status = HttpStatus.INTERNAL_SERVER_ERROR;
    //    }
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setLocation(URI.create("/adverts?pageNumber=1&pageSize=10"));
    //    return new ResponseEntity<>(status);
    //}

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
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
    @PutMapping("/adverts/{id}")
    public ResponseEntity<Void> updateAdvert(@PathVariable("id") Long advertId,
                                             @RequestBody AdvertFormData advert,
                                             Principal principal) {
        HttpStatus status = HttpStatus.OK;
        //Users user = noRegUI.getUserByPrincipal(principal);
        Users user = usersService.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.FORBIDDEN;
        }
        else {
            Adverts original = advertsService.getAdvertById(advertId);
            if (advert == null || original == null) {
                status = HttpStatus.BAD_REQUEST;
            }
            else {
                Adverts updated = advert.mapToAdverts();
                updated.setId(advertId);
                updated.setAuthorId(original.getAuthorId());
                advertsService.updateAdvert(updated);
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts/" + advertId.toString()));
        return new ResponseEntity<>(headers, status);
    }

    //@Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
    //        responses = {@ApiResponse(responseCode = "200", description = "Successfully return adverts page content"),
    //                     @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
    //        description = "Role required: ROLE_MOD")
    //@PatchMapping("/advert/check")
    //public ResponseEntity<Void> toggleModCheckOnAdvert(@RequestParam(value = "id") Long advertId) {
    //    HttpStatus status = HttpStatus.OK;
    //    if (!modUI.changeAdvertModCheck(advertId)) {
    //        status = HttpStatus.NOT_FOUND;
    //    }
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setLocation(URI.create("/advert?id=" + advertId.toString()));
    //    return new ResponseEntity<>(headers, status);
    //}

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully delete advert"),
                         @ApiResponse(responseCode = "403", description = "User unauthorized"),
                         @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
                         @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
            description = "Authorization required, ownership checked")
    @DeleteMapping("/adverts/{id}")
    public ResponseEntity<Void> deleteAdvert(@PathVariable("id") Long advertId,
                                             Principal principal) {
        HttpStatus status = HttpStatus.OK;
        //Users user = noRegUI.getUserByPrincipal(principal);
        Users user = usersService.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
        }
        else {
            //Adverts advert = clientUI.getAdvert(advertId);
            Adverts advert = advertsService.getAdvertById(advertId);
            if (advert == null) {
                status = HttpStatus.NOT_FOUND;
            }
            else if (!user.getId().equals(advert.getAuthorId())) {
                status = HttpStatus.FORBIDDEN;
            }
            //else {
            //    advertsService.delete(advertsService.getAdvertDTOById(advertId));
            //}
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts"));
        return new ResponseEntity<>(headers, status);
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully change advert status"),
                    @ApiResponse(responseCode = "403", description = "User unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
            description = "Authorization required, ownership checked")
    @PatchMapping("/advert/{id}/status")
    public ResponseEntity<Void> changeAdvertStatus(@PathVariable("id") Long advertId,
                                                   @RequestBody AdvertStatus advertStatus,
                                                   Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Users user = usersService.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
        }
        else {
            Adverts advert = advertsService.getAdvertById(advertId);
            if (advert == null) {
                status = HttpStatus.NOT_FOUND;
            }
            else {
                advertsService.changeAdvertStatus(advertId, advertStatus);
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts"));
        return new ResponseEntity<>(headers, status);
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully change advert mod check"),
                    @ApiResponse(responseCode = "403", description = "User unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
            description = "Authorization required, ownership checked")
    @PatchMapping("/advert/{id}/check")
    public ResponseEntity<Void> checkAdvert(@PathVariable("id") Long advertId,
                                            @RequestBody AdvertStatus advertStatus,
                                            Principal principal) {
        HttpStatus status = HttpStatus.OK;
        Users user = usersService.getUserByPrincipal(principal);
        if (user == null) {
            status = HttpStatus.UNAUTHORIZED;
        }
        else {
            Adverts advert = advertsService.getAdvertById(advertId);
            if (advert == null) {
                status = HttpStatus.NOT_FOUND;
            }
            else {
                advertsService.changeAdvertModCheck(advertId);
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts"));
        return new ResponseEntity<>(headers, status);
    }

    //@Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
    //        responses = {@ApiResponse(responseCode = "200", description = "Successfully hide advert"),
    //                @ApiResponse(responseCode = "403", description = "User unauthorized"),
    //                @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
    //                @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
    //        description = "Authorization required, ownership checked")
    //@PatchMapping("/advert/hide")
    //public ResponseEntity<Void> hideAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
    //    HttpStatus status = HttpStatus.OK;
    //    Users user = noRegUI.getUserByPrincipal(principal);
    //    if (user == null) {
    //        status = HttpStatus.UNAUTHORIZED;
    //    }
    //    else {
    //        Adverts advert = clientUI.getAdvert(advertId);
    //        if (!user.getId().equals(advert.getAuthorId())) {
    //            status = HttpStatus.FORBIDDEN;
    //        }
    //        else if (!clientUI.hideAdvert(advertId)) {
    //            status = HttpStatus.NOT_FOUND;
    //        }
    //    }
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setLocation(URI.create("/adverts/private"));
    //    return new ResponseEntity<>(headers, status);
    //}
//
    //@Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
    //        responses = {@ApiResponse(responseCode = "200", description = "Successfully reveal advert"),
    //                @ApiResponse(responseCode = "403", description = "User unauthorized"),
    //                @ApiResponse(responseCode = "400", description = "Logically forbidden for this role to access"),
    //                @ApiResponse(responseCode = "404", description = "Invalid Id or Advert not found")},
    //        description = "Authorization required, ownership checked")
    //@PatchMapping("/advert/reveal")
    //public ResponseEntity<Void> revealAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
    //    HttpStatus status = HttpStatus.OK;
    //    Users user = noRegUI.getUserByPrincipal(principal);
    //    if (user == null) {
    //        status = HttpStatus.UNAUTHORIZED;
    //    }
    //    else {
    //        Adverts advert = clientUI.getAdvert(advertId);
    //        if (!user.getId().equals(advert.getAuthorId())) {
    //            status = HttpStatus.FORBIDDEN;
    //        }
    //        else if (!clientUI.revealAdvert(advertId)) {
    //            status = HttpStatus.NOT_FOUND;
    //        }
    //    }
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setLocation(URI.create("/adverts/private"));
    //    return new ResponseEntity<>(headers, status);
    //}
}
