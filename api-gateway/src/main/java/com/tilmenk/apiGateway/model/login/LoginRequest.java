package com.tilmenk.apiGateway.model.login;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
