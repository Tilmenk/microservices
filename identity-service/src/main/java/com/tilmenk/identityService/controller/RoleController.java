package com.tilmenk.identityService.controller;

import com.tilmenk.identityService.model.Role;
import com.tilmenk.identityService.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getUsers() {
        return ResponseEntity.ok().body(roleService.getRoles());
    }

}
