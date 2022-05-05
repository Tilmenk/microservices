package com.tilmenk.teamService.model.apiGateway;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteTeamData implements Serializable {
    private Long teamId;
    private String userThatIsDeleting;

}
