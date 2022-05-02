package com.tilmenk.currencyService.controller;


import com.tilmenk.currencyService.model.Currencies;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path="api/currency")
public class CurrencyServiceController {
    @Operation(summary = "get currency translations for base price")
    @GetMapping("/{price}")
    public Currencies getCurrencyValuesForBasePrice(@PathVariable double price) {
        return calculateCurrenciesForBasePrice(price);
    }

    private Currencies calculateCurrenciesForBasePrice(double price) {
        return  Currencies.builder().euro(price).bitcoin(price/36000).dollar(price*0.7).build();
    }

}
