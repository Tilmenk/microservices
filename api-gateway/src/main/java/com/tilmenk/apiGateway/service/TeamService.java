package com.tilmenk.apiGateway.service;

import com.tilmenk.apiGateway.model.Team;
import com.tilmenk.apiGateway.requestBodies.DeleteTeamBody;

import java.util.List;

public interface TeamService {

    List<Team> getTeams();

    void saveTeam(Team team);

    void deleteTeam(DeleteTeamBody deleteTeamBody);

}
