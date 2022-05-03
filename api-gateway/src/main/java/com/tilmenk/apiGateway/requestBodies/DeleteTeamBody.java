package com.tilmenk.apiGateway.requestBodies;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DeleteTeamBody implements Serializable {
    private Long teamId;
    private String userThatIsDeleting;


}
