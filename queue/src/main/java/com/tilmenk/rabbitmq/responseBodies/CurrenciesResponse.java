package com.tilmenk.rabbitmq.responseBodies;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record CurrenciesResponse(double euro, double bitcoin, double dollar) implements Serializable {
}
