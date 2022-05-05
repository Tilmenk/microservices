package com.tilmenk.apiGateway.service;


import com.tilmenk.apiGateway.model.identityService.CreateUserRequest;
import com.tilmenk.apiGateway.model.identityService.User;

public interface UserService {
    User saveUser(CreateUserRequest user);


}
