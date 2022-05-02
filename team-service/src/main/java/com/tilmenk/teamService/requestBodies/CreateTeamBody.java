package com.tilmenk.teamService.requestBodies;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateTeamBody implements Serializable {

    private String name;
    private String creator;
    private List<String> pokemonNames;

}
