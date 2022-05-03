package com.tilmenk.priceService.controller;


import com.tilmenk.priceService.model.Pokemon;
import com.tilmenk.priceService.model.Team;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path="api/price")
public class PriceServiceController {
    @Operation(summary = "get Price for a Team of Pokemon")
    @GetMapping("/team")
    public Integer getPriceForTeam(@RequestBody Team team) {
        return calculatePriceForTeam(team);
    }
    @Operation(summary = "get Price for a single Pokemon")

    @GetMapping("/pokemon")
    public Integer getPriceForPokemon(@RequestBody Pokemon pokemon) {
        return calculatePriceForPokemon(pokemon);
    }

    private Integer calculatePriceForTeam(Team team) {
        AtomicInteger price = new AtomicInteger();
        team.getPokemon().forEach(pokemon -> price.addAndGet(calculatePriceForPokemon(pokemon)));
        return price.get();
    }
    private Integer calculatePriceForPokemon(Pokemon pokemon) {
        int tempPrice =
                (pokemon.getAttack() + pokemon.getAttack_sp() + pokemon.getDefense() + pokemon.getDefense_sp() + pokemon.getSpeed() + pokemon.getHealth()) / 6;
            if (pokemon.getLegendary()) tempPrice *= 1.5;
            return tempPrice;
        }
}
