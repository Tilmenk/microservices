package com.tilmenk.identityService.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Table(name = "myUser")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;
    private String email;
    private String firstName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
