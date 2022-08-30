package com.tilmenk.identityService.service;

import com.tilmenk.identityService.model.Role;
import com.tilmenk.identityService.repository.RoleRepo;
import com.tilmenk.identityService.repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestRoleServiceImpl {
    RoleService roleService;
    UserRepo userRepoMock;
    RoleRepo roleRepoMock;
    PasswordEncoder passwordEncoderMock;
    Role role1Mock;
    Role role2Mock;

    @BeforeEach
    void setUp() {

        this.userRepoMock = Mockito.mock(UserRepo.class);
        this.roleRepoMock = Mockito.mock(RoleRepo.class);
        this.passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        this.role1Mock = Mockito.mock(Role.class);
        this.role2Mock = Mockito.mock(Role.class);
        this.roleService = new RoleServiceImpl(userRepoMock, roleRepoMock, passwordEncoderMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveRole() {
        //GIVEN
        Role r1 = new Role(1234, "name");
        //WHEN
        Mockito.when(roleRepoMock.findByName("name")).thenReturn(Optional.empty());
        //THEN
        assertDoesNotThrow(() -> roleService.saveRole(r1));
    }

    @Test
    void saveRoleException() {
        //GIVEN
        Role r1 = new Role(1234, "name");
        Optional<Role> optionalFilled = Optional.of(r1);
        //WHEN
        Mockito.when(roleRepoMock.findByName("name")).thenReturn(optionalFilled);
        //THEN
        assertThrows(IllegalStateException.class, () -> roleService.saveRole(r1));
    }

    @Test
    void getRoles() {
        //GIVEN
        Role r1 = new Role(1234, "r1");
        Role r2 = new Role(1235, "r2");
        List<Role> listOfRoles = new LinkedList<>();
        listOfRoles.add(r1);
        listOfRoles.add(r2);
        //WHEN
        Mockito.when(roleRepoMock.findAll()).thenReturn(listOfRoles);
        List<Role> res = roleService.getRoles();
        //THEN
        assertEquals(listOfRoles.get(0), res.get(0));
    }

    @Test
    void getRolesEmptyList() {
        //GIVEN
        List<Role> listOfRoles = new LinkedList<>();
        //WHEN
        Mockito.when(roleRepoMock.findAll()).thenReturn(listOfRoles);
        List<Role> res = roleService.getRoles();
        //THEN
        assertTrue(res.isEmpty());
    }

    @Test
    void addRoleToUser() {
        //GIVEN

        //WHEN

        //THEN

    }
}