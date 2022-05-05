package com.tilmenk.identityService.model.apiGateway;

import lombok.Data;

@Data
public class CreateUserRequest {
    String email;
    String firstName;
    String password;
}
