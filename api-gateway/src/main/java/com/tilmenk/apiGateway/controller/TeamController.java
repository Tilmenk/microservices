package com.tilmenk.apiGateway.controller;


import com.tilmenk.apiGateway.model.Team;
import com.tilmenk.apiGateway.requestBodies.DeleteTeamBody;
import com.tilmenk.apiGateway.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.security.Principal;

@RestController
@RequestMapping(path = "api" + "/team")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    ResponseEntity<Object> getTeams() {
        try {
            return ResponseEntity.ok().body(teamService.getTeams());
        } catch (WebClientResponseException e) {
            return ResponseEntity.internalServerError().body(e.getResponseBodyAsString());
        }
    }

    @PostMapping
    @ResponseBody
    ResponseEntity<Object> saveTeam(Principal principal,
                                    @RequestBody Team team) {
        try {
            team.setCreator(principal.getName());
            teamService.saveTeam(team);
            return ResponseEntity.ok().body("Team created!");
        } catch (WebClientResponseException e) {
            return ResponseEntity.internalServerError().body(e.getResponseBodyAsString());
        }
    }

    @DeleteMapping(path = "/{teamId}")
    @ResponseBody
    ResponseEntity<Object> deleteTeam(Principal principal,
                                      @PathVariable Long teamId) {
        try {
            teamService.deleteTeam(new DeleteTeamBody(teamId,
                    principal.getName()));
            return ResponseEntity.ok().body("Team deleted!");
        } catch (WebClientResponseException e) {
            return ResponseEntity.internalServerError().body(e.getResponseBodyAsString());
        }
    }
}
