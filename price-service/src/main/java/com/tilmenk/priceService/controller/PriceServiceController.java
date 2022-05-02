package com.tilmenk.priceService.controller;


import com.tilmenk.priceService.model.Pokemon;
import com.tilmenk.priceService.model.Team;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(path = "api/price")
public class PriceServiceController {
    @Operation(summary = "get Price for a Team of Pokemon")
    @PostMapping("/team")
    public double getPriceForTeam(@RequestBody Team team) {
        return calculatePriceForTeam(team);
    }

    @Operation(summary = "get Price for a single Pokemon")

    @PostMapping("/pokemon")
    public double getPriceForPokemon(@RequestBody Pokemon pokemon) {
        return calculatePriceForPokemon(pokemon);
    }

    private double calculatePriceForTeam(Team team) {
        AtomicReference<Double> price = new AtomicReference<>((double) 0);
        team.getPokemon().forEach(pokemon -> price.updateAndGet(v -> (v + calculatePriceForPokemon(pokemon))));
        return price.get();
    }

    private double calculatePriceForPokemon(Pokemon pokemon) {
        int tempPrice =
                (pokemon.getAttack() + pokemon.getAttack_sp() + pokemon.getDefense() + pokemon.getDefense_sp() + pokemon.getSpeed() + pokemon.getHealth()) / 6;
        if (pokemon.getLegendary()) tempPrice *= 1.5;
        return tempPrice;
    }
}
