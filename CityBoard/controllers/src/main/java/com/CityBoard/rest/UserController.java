package com.CityBoard.rest;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.services.UsersService;
import com.CityBoard.ui.AdminUI;
import com.CityBoard.ui.NoRegUI;
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

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
@OpenAPIDefinition(info = @Info(title = "CityBoard API", version = "v1"))

@Tag(name = "User API")
@RestController
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"),
               responses = {@ApiResponse(responseCode = "200", description = "Successfully return users page content")},
               description = "Role required: ADMIN_ROLE")
    @GetMapping("/users")
    public ResponseEntity<Paged<Users>> showUsersPaged(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Page<Users> usersPage = usersService.getAllUsersPage(currentPage, pageSize);
        Paged<Users> usersPaged = new Paged<>(usersPage, Paging.of(usersPage.getTotalPages(), currentPage, pageSize));
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
    @DeleteMapping("/users/{id}/roles")
    public ResponseEntity<Void> removeUserRole(@PathVariable("id") Long id,
                                               @RequestBody Roles role) {
        if (usersService.removeRole(id, role)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/users"));
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully added role, return to /users",
                    links = {@Link(name = "Get users page", operationId = "getUsersPaged",
                            parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                                    @LinkParameter(name = "pageSize", expression = "10")}
                    )}),
                    @ApiResponse(responseCode = "404", description = "User not found")},
            description = "Role required: ADMIN_ROLE")
    @PutMapping("/users/{id}/roles")
    public ResponseEntity<Void> addUserRole(@RequestParam("id") Long id,
                                            @RequestBody Roles role) {
        if (usersService.addRole(id, role)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/users"));
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
