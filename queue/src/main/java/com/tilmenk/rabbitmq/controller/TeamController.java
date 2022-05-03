package com.tilmenk.rabbitmq.controller;


import com.tilmenk.rabbitmq.model.Pokemon;
import com.tilmenk.rabbitmq.model.TeamWithActualPokemon;
import com.tilmenk.rabbitmq.model.TeamWithPokemonNames;
import com.tilmenk.rabbitmq.publisher.TeamPublisher;
import com.tilmenk.rabbitmq.requestBodies.DeleteTeamBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api" + "/team")
@Slf4j
public class TeamController {


    private final TeamPublisher teamPublisher;

    @Autowired
    public TeamController(TeamPublisher teamPublisher) {
        this.teamPublisher = teamPublisher;
    }

    @GetMapping
    public List<TeamWithPokemonNames> getTeams() {
        List<TeamWithPokemonNames> teamsForResponse = new ArrayList<>();
        log.info("Controller - getPokemons");
        for (TeamWithActualPokemon team : teamPublisher.publishFetchTeams()) {
            TeamWithPokemonNames teamForResponse = new TeamWithPokemonNames();
            teamForResponse.setCosts(team.getCosts());
            teamForResponse.setCreator(team.getCreator());
            teamForResponse.setId(team.getId());
            teamForResponse.setName(team.getName());
            List<String> pokemonNames = new ArrayList<>();
            for (Pokemon pokemon : team.getPokemon()) {
                pokemonNames.add(pokemon.getName());
            }
            teamForResponse.setPokemon(pokemonNames);

            teamsForResponse.add(teamForResponse);
        }
        return teamsForResponse;
    }

    @PostMapping
    public ResponseEntity<Object> createTeam(@RequestBody TeamWithPokemonNames team) {
        return teamPublisher.publishCreateTeam(team);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteTeam(@RequestBody DeleteTeamBody deleteTeamBody) {
        return teamPublisher.publishDeleteTeam(deleteTeamBody);
    }

}
