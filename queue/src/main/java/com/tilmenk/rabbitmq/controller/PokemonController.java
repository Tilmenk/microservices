package com.tilmenk.rabbitmq.controller;


import com.tilmenk.rabbitmq.model.Pokemon;
import com.tilmenk.rabbitmq.publisher.PokemonPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api" + "/pokemon")
@Slf4j
public class PokemonController {

    private final PokemonPublisher pokemonPublisher;

    @Autowired
    public PokemonController(PokemonPublisher pokemonPublisher) {
        this.pokemonPublisher = pokemonPublisher;
    }


    @GetMapping
    public List<Pokemon> getPokemons() {
        log.info("Controller - getPokemons");
        return pokemonPublisher.fetchPokemons();
    }
}
