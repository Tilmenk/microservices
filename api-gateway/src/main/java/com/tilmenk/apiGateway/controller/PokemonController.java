package com.tilmenk.apiGateway.controller;


import com.tilmenk.apiGateway.service.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping(path = "api" + "/pokemon")
@RequiredArgsConstructor
@Slf4j
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping
    ResponseEntity<Object> getPokemons() {
        try {
            return ResponseEntity.ok().body(pokemonService.getPokemons());
        } catch (WebClientResponseException e) {
            return ResponseEntity.internalServerError().body(e.getResponseBodyAsString());
        }
    }

}
