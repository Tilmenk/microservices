package com.tilmenk.teamService.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record Currencies(double euro, double bitcoin,
                         double dollar) implements Serializable {
}
