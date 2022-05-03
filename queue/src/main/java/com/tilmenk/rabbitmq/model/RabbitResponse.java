package com.tilmenk.rabbitmq.model;

import java.io.Serializable;

public record RabbitResponse(boolean success, String message) implements Serializable {
}
