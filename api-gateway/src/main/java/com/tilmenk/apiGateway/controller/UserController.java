package com.tilmenk.apiGateway.controller;


import com.tilmenk.apiGateway.model.MyHttpResponse;
import com.tilmenk.apiGateway.model.identityService.CreateUserRequest;
import com.tilmenk.apiGateway.model.identityService.User;
import com.tilmenk.apiGateway.model.login.LoginRequest;
import com.tilmenk.apiGateway.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(path = "api" + "/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "create a new user", description = "email has to be "
            + "unique")
    @PostMapping()
    public ResponseEntity<MyHttpResponse<User>> saveUser(@RequestBody CreateUserRequest user) {
        try {
            return ResponseEntity.created(URI.create("/api/user")).body(new MyHttpResponse<>(userService.saveUser(user), "success"));
        } catch (WebClientResponseException e) {
            log.error(e.getResponseBodyAsString());
            return ResponseEntity.internalServerError().body(new MyHttpResponse<>(e.getResponseBodyAsString()));
        }
    }

    @Operation(summary = "Get the name from JWT")
    @GetMapping(path = "/getName")
    @ResponseBody
    public ResponseEntity<MyHttpResponse<String>> getName(Principal principal) {
        return ResponseEntity.ok().body(new MyHttpResponse<String>(principal.getName()));
    }

    @Operation(summary = "Login")
    @PostMapping(path = "/login")
    @ResponseBody
    public ResponseEntity<MyHttpResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().build();
    }

}
