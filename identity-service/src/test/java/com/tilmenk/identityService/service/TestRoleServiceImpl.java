package com.tilmenk.identityService.service;

import com.tilmenk.identityService.model.Role;
import com.tilmenk.identityService.model.User;
import com.tilmenk.identityService.repository.RoleRepo;
import com.tilmenk.identityService.repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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
    Role r1;
    Role r2;

    @BeforeEach
    void setUp() {
        this.userRepoMock = Mockito.mock(UserRepo.class);
        this.roleRepoMock = Mockito.mock(RoleRepo.class);
        this.passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        this.r1 = new Role(1234, "r1");
        this.r2 = new Role(1235, "r2");
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
        String email = "ida@loenneberga.se";
        String roleName = "role";
        Role role1 = new Role("role1");
        Role role2 = new Role("role2");
        Role role3 = new Role("role3");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        User user = new User(12345L, email, "Ida", "password", roleList);
        User spyUser = Mockito.spy(user);
        //WHEN
        Mockito.when(userRepoMock.findByEmail(email)).thenReturn(Optional.of(spyUser));
        Mockito.when(roleRepoMock.findByName(roleName)).thenReturn(Optional.of(role2));

        roleService.addRoleToUser(email, "role2");
        //THEN
        Mockito.verify(spyUser).getRoles().add(r1);
    }
}