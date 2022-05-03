package com.tilmenk.apiGateway.service;

import com.tilmenk.apiGateway.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {

    private static final String QUEUE_URL =
            "http" + "://localhost" + ":8083" + "/api";

    private final WebClient queueClient;

    @Autowired
    public PokemonServiceImpl(WebClient.Builder clientBuilder) {
        this.queueClient = clientBuilder.baseUrl(QUEUE_URL).build();
    }

    @Override
    public List<Pokemon> getPokemons() {
        return queueClient.get().uri("/pokemon").retrieve().bodyToFlux(Pokemon.class).collectList().block();
    }
}
