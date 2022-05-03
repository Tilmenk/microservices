package com.tilmenk.rabbitmq.requestBodies;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteTeamBody implements Serializable {
    private Long teamId;
    private String userThatIsDeleting;

}
