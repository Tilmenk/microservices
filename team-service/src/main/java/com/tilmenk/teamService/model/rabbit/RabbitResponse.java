package com.tilmenk.teamService.model.rabbit;

import java.io.Serializable;

public record RabbitResponse(boolean success,
                             String message) implements Serializable {
}
