package com.tilmenk.identityService.service;

import com.tilmenk.identityService.model.Role;
import com.tilmenk.identityService.model.User;
import com.tilmenk.identityService.repository.RoleRepo;
import com.tilmenk.identityService.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestUserServiceImpl {
    UserService userService;
    UserRepo userRepoMock;
    RoleRepo roleRepoMock;
    PasswordEncoder passwordEncoderMock;

    @BeforeEach
    void setUp() {
        this.userRepoMock = Mockito.mock(UserRepo.class);
        this.roleRepoMock = Mockito.mock(RoleRepo.class);
        this.passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        this.userService = new UserServiceImpl(userRepoMock, roleRepoMock, passwordEncoderMock);
    }

    @Test
    void saveUser() {
        //GIVEN
        Role role1 = new Role("role1");
        Role role2 = new Role("role2");
        String email = "email";
        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        User user = new User(12345L, email, "Ida", "password", roleList);
        //WHEN
        Mockito.when(userRepoMock.findByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(userRepoMock.save(user)).thenReturn(user);
        Mockito.when(roleRepoMock.findByName("user_default")).thenReturn(Optional.of(new Role()));
        //THEN
        assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    void saveUserException() {
        //GIVEN
        Role role1 = new Role("role1");
        Role role2 = new Role("role2");
        String email = "email";
        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        User user = new User(12345L, email, "Ida", "password", roleList);
        //WHEN
        Mockito.when(userRepoMock.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(userRepoMock.save(user)).thenReturn(user);
        Mockito.when(roleRepoMock.findByName("user_default")).thenReturn(Optional.of(new Role()));
        //THEN
        assertThrows(IllegalStateException.class, () -> userService.saveUser(user));
    }

}