package com.tilmenk.apiGateway.model.teamService.currencyService;

import java.io.Serializable;

public record Currencies(double euro, double bitcoin,
                         double dollar) implements Serializable {
}
