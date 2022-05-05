package com.tilmenk.apiGateway.model.teamService;

import lombok.Data;

import java.util.List;

@Data
public class CreateTeamRequest {
    String name;
    List<String> pokemon;
}
