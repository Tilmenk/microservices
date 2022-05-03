package com.tilmenk.rabbitmq.model;


import com.tilmenk.rabbitmq.responseBodies.CurrenciesResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Team implements Serializable {

    private Long id;
    private String name;
    private String creator;
    private CurrenciesResponse costs;

    public Team(String name, String creator, CurrenciesResponse costs) {
        this.name = name;
        this.creator = creator;
        this.costs = costs;
    }

}
