package com.tilmenk.apiGateway.model.teamService;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DeleteTeamData implements Serializable {
    private Long teamId;
    private String userThatIsDeleting;


}
