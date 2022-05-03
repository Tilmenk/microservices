package com.tilmenk.rabbitmq.model;


import com.tilmenk.rabbitmq.responseBodies.CurrenciesResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeamWithPokemonNames extends Team implements Serializable {

    private List<String> pokemon;

    public TeamWithPokemonNames(String name, String creator, CurrenciesResponse costs, List<String> pokemon) {
        super(name, creator, costs);
        this.pokemon = pokemon;
    }


}
