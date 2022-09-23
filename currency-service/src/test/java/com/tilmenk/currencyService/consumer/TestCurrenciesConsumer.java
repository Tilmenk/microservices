package com.tilmenk.currencyService.consumer;

import com.tilmenk.currencyService.model.Currencies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCurrenciesConsumer {
    private CurrenciesConsumer currenciesConsumer;
    @BeforeEach
    void setUp() {
        currenciesConsumer = new CurrenciesConsumer();
    }

    @Test
    void consumeAction_FetchCurrencies_forCosts() {
        //GIVEN
        double costsEuro = 100.0;
        double costsDollar = 70.0;
        double costsBitcoin = 0.002777777777777778;
        //WHEN
        Currencies res = currenciesConsumer.consumeAction_FetchCurrencies_forCosts(costsEuro);
        //THEN
        assertEquals(costsDollar, res.dollar());
        assertEquals(costsEuro, res.euro());
        assertEquals(costsBitcoin, res.bitcoin());
    }

    @Test
    void consumeAction_FetchCurrencies_forCosts_ArithmeticException() {
        //GIVEN
        double costsEuro = 0;
        double negativeBitcoin = -1.0;
        //WHEN
        Currencies res = currenciesConsumer.consumeAction_FetchCurrencies_forCosts(costsEuro);
        //THEN
        assertEquals(negativeBitcoin, res.bitcoin());
    }


}