package com.tilmenk.apiGateway.model.identityService;


import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;
    private String email;
    private String firstName;
    private String password;

    private Collection<Role> roles = new ArrayList<>();
}
