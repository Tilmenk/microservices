package com.tilmenk.apiGateway.model.rabbit;

import java.io.Serializable;

public record RabbitResponse(boolean success,
                             String message) implements Serializable {
}
