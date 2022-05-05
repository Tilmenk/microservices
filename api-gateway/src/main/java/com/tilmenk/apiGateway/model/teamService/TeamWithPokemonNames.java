package com.tilmenk.apiGateway.model.teamService;

import com.tilmenk.apiGateway.model.teamService.currencyService.Currencies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamWithPokemonNames implements Serializable {

    private Long id;
    private List<String> pokemon;

    private String name;
    private String creator;
    private Currencies costs;


}
