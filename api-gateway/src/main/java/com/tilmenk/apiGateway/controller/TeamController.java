package com.tilmenk.apiGateway.controller;


import com.tilmenk.apiGateway.model.MyHttpResponse;
import com.tilmenk.apiGateway.model.rabbit.RabbitResponse;
import com.tilmenk.apiGateway.model.teamService.CreateTeamRequest;
import com.tilmenk.apiGateway.model.teamService.DeleteTeamData;
import com.tilmenk.apiGateway.model.teamService.TeamWithPokemonNames;
import com.tilmenk.apiGateway.publisher.TeamPublisher;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "api" + "/team")
@RequiredArgsConstructor
@Slf4j
public class TeamController {
    private final TeamPublisher teamPublisher;

    @Operation(summary = "get all teams", description = "returns an array of "
            + "Teams")
    @GetMapping
    ResponseEntity<MyHttpResponse<List<TeamWithPokemonNames>>> getTeams() {
        try {
            return ResponseEntity.ok().body(new MyHttpResponse<List<TeamWithPokemonNames>>(teamPublisher.publishFetchTeams(), "success"));
        } catch (WebClientResponseException e) {
            return ResponseEntity.internalServerError().body(new MyHttpResponse<>(e.getResponseBodyAsString()));
        }
    }

    @Operation(summary = "create a new team", description =
            "name has to be " + "unique for user, 6 pokemon needed, " +
                    "pokemonNames have to match a " + "pokemon in " + "team" + "-service DB")
    @PostMapping
    @ResponseBody
    ResponseEntity<String> saveTeam(Principal principal,
                                    @RequestBody CreateTeamRequest teamRequest) {
        TeamWithPokemonNames teamToCreate = new TeamWithPokemonNames();
        teamToCreate.setCreator(principal.getName());
        teamToCreate.setName(teamRequest.getName());
        teamToCreate.setPokemon(teamRequest.getPokemon());
        RabbitResponse response = teamPublisher.publishSaveTeam(teamToCreate);
        return response.success() ?
                ResponseEntity.ok().body(response.message()) :
                ResponseEntity.status(HttpStatus.CONFLICT).body(response.message());
    }

    @Operation(summary = "delet an existing team", description =
            "deleter " + "has" + " to be creator of team")
    @DeleteMapping(path = "/{teamId}")
    @ResponseBody
    ResponseEntity<String> deleteTeam(Principal principal,
                                      @PathVariable Long teamId) {
        RabbitResponse response =
                teamPublisher.publishDeleteTeam(new DeleteTeamData(teamId,
                        principal.getName()));
        return response.success() ?
                ResponseEntity.ok().body(response.message()) :
                ResponseEntity.status(HttpStatus.CONFLICT).body(response.message());
    }
}
