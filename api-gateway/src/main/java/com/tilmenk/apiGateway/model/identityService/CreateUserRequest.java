package com.tilmenk.apiGateway.model.identityService;

import lombok.Data;

@Data
public class CreateUserRequest {
    String email;
    String firstName;
    String password;
}
