package com.tilmenk.identityService.service;

import com.tilmenk.identityService.model.Role;

import java.util.List;

public interface RoleService {

    Role saveRole(Role role) throws IllegalStateException;

    List<Role> getRoles();

    void addRoleToUser(String email, String roleName);

}
