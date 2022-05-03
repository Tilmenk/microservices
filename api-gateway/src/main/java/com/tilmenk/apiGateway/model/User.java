package com.tilmenk.apiGateway.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String email;
    private String firstName;
    private String password;

    private Collection<Role> roles = new ArrayList<>();
}
