package com.tilmenk.teamService.controller;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api" + "/pokemon")

public class PokemonController {

    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Operation(summary = "get all pokemon")
    @GetMapping
    public List<Pokemon> getPokemon() {
         return pokemonService.getPokemonFromWarehouse();
    }

    @PostMapping
    public void evictCache() {
         pokemonService.evictCache();
    }



}
