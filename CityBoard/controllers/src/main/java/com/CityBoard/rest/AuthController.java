package com.CityBoard.rest;

import com.CityBoard.models.JwtRequest;
import com.CityBoard.models.JwtResponse;
import com.CityBoard.rest.data.RefreshJwtRequest;
import com.CityBoard.models.Users;
import com.CityBoard.rest.data.UserRegistrationData;
import com.CityBoard.services.AuthService;
import com.CityBoard.services.UsersService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import java.net.URI;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CityBoard API", version = "v1"))

@Tag(name = "Auth API")
@RestController
public class AuthController {
    private final AuthService authService;
    private final UsersService usersService;

    public AuthController(AuthService authService, UsersService usersService) {
        this.authService = authService;
        this.usersService = usersService;
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        try {
            final JwtResponse token = authService.login(authRequest);
            return ResponseEntity.ok(token);
        }
        catch (AuthException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        try {
            final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
            return ResponseEntity.ok(token);
        }
        catch (AuthException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        try {
            final JwtResponse token = authService.refresh(request.getRefreshToken());
            return ResponseEntity.ok(token);
        }
        catch (AuthException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch (IllegalStateException i) {
            // No refresh token
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(responses = {@ApiResponse(responseCode = "201", description = "New user created and saved in database",
            links = {@Link(name = "Get adverts page", operationId = "getAdvertsPaged",
                    parameters = { @LinkParameter(name = "pageNumber", expression = "1"),
                            @LinkParameter(name = "pageSize", expression = "10")})}),
            @ApiResponse(responseCode = "409", description = "Username exists in database")})
    @PostMapping("/registration")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationData data) {
        HttpHeaders headers = new HttpHeaders();
        Users user = data.mapToUsers();
        try {
            usersService.createUser(user);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        headers.setLocation(URI.create("/login"));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
