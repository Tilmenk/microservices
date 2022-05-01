package com.tilmenk.teamService.service;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.repository.PokemonRepository;
import com.tilmenk.teamService.responses.PriceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    private final WebClient localApiClient;
    private final RestTemplate restTemplate;



    @Autowired
    public PokemonService(PokemonRepository pokemonRepository,
                          WebClient.Builder wareHouseApiClientBuilder,
                          RestTemplate restTemplate) {
        this.pokemonRepository = pokemonRepository;
        this.localApiClient = wareHouseApiClientBuilder.baseUrl("http" +
                "://localhost:8080/api/pokemon").build();
        this.restTemplate = restTemplate;
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


    public void test () {

        PriceResponse priceResponse = restTemplate.getForObject(
                "http://localhost:8082/api/test/", PriceResponse.class
        );

        log.info(String.valueOf(priceResponse));
    }




}
