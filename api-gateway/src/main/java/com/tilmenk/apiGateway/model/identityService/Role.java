package com.tilmenk.apiGateway.model.identityService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private long Id;
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
