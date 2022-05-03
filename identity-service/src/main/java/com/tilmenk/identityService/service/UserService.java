package com.tilmenk.identityService.service;

import com.tilmenk.identityService.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);


    User getUser(String email);

    List<User> getUsers();
}
