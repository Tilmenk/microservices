package com.tilmenk.teamService.controller;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.model.Team;
import com.tilmenk.teamService.requestBodies.CreateTeamBody;
import com.tilmenk.teamService.service.PokemonService;
import com.tilmenk.teamService.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api" + "/team")

public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(summary = "get Teams out of this Service")
    @GetMapping
    public List<Team> getTeams(){
        return teamService.getTeams();
    }

    @Operation(summary = "create a Team for a User")
    @PostMapping
    public ResponseEntity createTeam(@RequestBody CreateTeamBody createTeamBody){
         teamService.createTeam(createTeamBody);
        return ResponseEntity.ok().build();
    }

}
