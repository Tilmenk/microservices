package com.tilmenk.currencyService.model;

import lombok.Builder;

@Builder
public record Currencies (double euro,
                          double bitcoin,
                          double dollar) {
}
