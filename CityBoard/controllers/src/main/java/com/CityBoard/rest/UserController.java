package com.CityBoard.rest;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.ui.AdminUI;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Configuration
@SecurityScheme(name = "basicAuth", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER,
description = "Roles: USER_ROLE, MOD_ROLE, ADMIN_ROLE")
@OpenAPIDefinition(info = @Info(title = "CityBoard API", version = "v1"))

@Tag(name = "User API")
@RestController
public class UserController {
    private final NoRegUI noRegUI;
    private final AdminUI adminUI;

    public UserController(NoRegUI noRegUI, AdminUI adminUI) {
        this.noRegUI = noRegUI;
        this.adminUI = adminUI;
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
               responses = {@ApiResponse(responseCode = "200", description = "Successfully return users page content")},
               description = "Role required: ADMIN_ROLE")
    @GetMapping("/users")
    public ResponseEntity<Paged<Users>> showUsersPaged(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Paged<Users> usersPaged = adminUI.getUsersPaged(currentPage, pageSize);
        return new ResponseEntity<>(usersPaged, HttpStatus.OK);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully removed role, return to /users",
                                      links = {@Link(name = "Get users page", operationId = "getUsersPaged",
                                                     parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                                                    @LinkParameter(name = "pageSize", expression = "10")}
                                      )}),
                         @ApiResponse(responseCode = "404", description = "User not found")},
            description = "Role required: ADMIN_ROLE")
    @PutMapping("/user/roles/remove")
    public ResponseEntity<Void> removeUserRole(@RequestParam("id") Long id,
                                               @RequestBody Roles role) {
        if (!adminUI.removeUserRole(id, role)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/users"));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully added role, return to /users",
                    links = {@Link(name = "Get users page", operationId = "getUsersPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                    @LinkParameter(name = "pageSize", expression = "10")}
                    )}),
                    @ApiResponse(responseCode = "404", description = "User not found")},
            description = "Role required: ADMIN_ROLE")
    @PutMapping("/user/roles/add")
    public ResponseEntity<Void> addUserRole(@RequestParam("id") Long id,
                                            @RequestBody Roles role) {
        if (!adminUI.addUserRole(id, role)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/users"));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(responses = {@ApiResponse(responseCode = "201", description = "New user created and saved in database",
                    links = {@Link(name = "Get adverts page", operationId = "getAdvertsPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                           @LinkParameter(name = "pageSize", expression = "10")})}),
                            @ApiResponse(responseCode = "409", description = "Username exists in database")})
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestParam("username") String username,
                                             @RequestParam("password") String password) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        if (!noRegUI.registerUser(user)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/adverts?pageNumber=1&pageSize=10"));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
