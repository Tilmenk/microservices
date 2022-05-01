package com.tilmenk.teamService.service;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.repository.PokemonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    private final WebClient localApiClient;


    @Autowired
    public PokemonService(PokemonRepository pokemonRepository,
                          WebClient.Builder wareHouseApiClientBuilder) {
        this.pokemonRepository = pokemonRepository;
        this.localApiClient = wareHouseApiClientBuilder.baseUrl("http" +
                "://localhost:8080/api/pokemon").build();
    }

    public List<Pokemon> getPokemon() {
        return pokemonRepository.findAll();
    }

    @Cacheable("pokemon")
    public List<Pokemon> getPokemonFromWarehouse() {
        log.info("Requesting Pokemon from warehouse..");
        return localApiClient.get().uri("/").retrieve().bodyToFlux(Pokemon.class).collectList().block();
    }

    @CacheEvict(value="pokemon", allEntries = true)
    public void evictCache() {
       log.info("evicting pokemon cache");
    }


}
