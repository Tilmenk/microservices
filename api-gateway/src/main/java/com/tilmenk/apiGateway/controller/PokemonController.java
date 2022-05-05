package com.tilmenk.apiGateway.controller;


import com.tilmenk.apiGateway.model.MyHttpResponse;
import com.tilmenk.apiGateway.model.teamService.Pokemon;
import com.tilmenk.apiGateway.publisher.PokemonPublisher;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@RestController
@RequestMapping(path = "api" + "/pokemon")
@RequiredArgsConstructor
@Slf4j
public class PokemonController {
    private final PokemonPublisher pokemonPublisher;

    @Operation(summary = "get all pokemons", description = "returns an array "
            + "of Pokemon with its costs in 3 currencies")
    @GetMapping
    ResponseEntity<MyHttpResponse<List<Pokemon>>> getPokemons() {
        try {
            return ResponseEntity.ok().body(new MyHttpResponse<List<Pokemon>>(pokemonPublisher.publishFetchPokemons(), "success"));
        } catch (WebClientResponseException e) {
            return ResponseEntity.internalServerError().body(new MyHttpResponse<>(e.getResponseBodyAsString()));
        }
    }

}
