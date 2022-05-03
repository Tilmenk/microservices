package com.tilmenk.apiGateway.controller;


import com.tilmenk.apiGateway.model.User;
import com.tilmenk.apiGateway.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;

@RestController
@RequestMapping(path = "api" + "/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Object> saveUser(@RequestBody User user) {
        try {
            return ResponseEntity.created(URI.create("/api/user")).body(userService.saveUser(user));
        } catch (WebClientResponseException e) {
            log.error(e.getResponseBodyAsString());
            return ResponseEntity.internalServerError().body(e.getResponseBodyAsString());
        }
    }
}
