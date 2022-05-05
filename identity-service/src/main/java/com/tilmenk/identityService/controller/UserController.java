package com.tilmenk.identityService.controller;

import com.tilmenk.identityService.model.User;
import com.tilmenk.identityService.model.apiGateway.CreateUserRequest;
import com.tilmenk.identityService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getUser(email));
    }

    @PostMapping("/user")
    public ResponseEntity<Object> saveUser(@RequestBody CreateUserRequest user) {
        try {
            return ResponseEntity.created(URI.create("/api/user")).body(userService.saveUser(userService.saveUser(User.builder().email(user.getEmail()).firstName(user.getFirstName()).password(user.getPassword()).build())));
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
