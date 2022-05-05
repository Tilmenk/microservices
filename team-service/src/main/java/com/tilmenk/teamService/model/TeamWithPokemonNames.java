package com.tilmenk.teamService.model;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TeamWithPokemonNames implements Serializable {

    private Long id;
    private List<String> pokemon;
    private String name;
    private String creator;

    private Currencies costs;

}
