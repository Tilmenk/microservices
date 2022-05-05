package com.tilmenk.teamService.service;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.model.externalApiResponse.ExternalPokeApiResponse;
import com.tilmenk.teamService.repository.PokemonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PokemonService {
    //deprecated
    //private final RestTemplate restTemplate;
    private static final String WAREHOUSE_URL_POKEMON = "http" +
            "://localhost:8080/api/pokemon";
    private static final String EXTERNAL_POKEAPI_URL = "https://pokeapi" +
            ".co/api/v2/pokemon/";
    private final PokemonRepository pokemonRepository;
    private final WebClient wareHouseApiClient;
    private final WebClient externalPokeApiClient;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepository,
                          WebClient.Builder wareHouseApiClientBuilder) {
        this.pokemonRepository = pokemonRepository;
        this.wareHouseApiClient =
                wareHouseApiClientBuilder.baseUrl(WAREHOUSE_URL_POKEMON).build();
        this.externalPokeApiClient =
                wareHouseApiClientBuilder.baseUrl(EXTERNAL_POKEAPI_URL).build();
    }


    @Cacheable("pokemonsInThisService")
    public List<Pokemon> getPokemons() {
        return pokemonRepository.findAll();
    }


    // warehouse
    private List<Pokemon> fetchPokemonsFromWarehouse() {
        try {
            log.info("fetching pokemon from warehouse");
            return wareHouseApiClient.get().uri("/").retrieve().bodyToFlux(Pokemon.class).collectList().block();
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return List.of();
    }

    private void addPokemonsToServiceDb(List<Pokemon> pokemonsToAdd) {
        for (Pokemon pokemon : pokemonsToAdd) {
            Optional<Pokemon> pokemonFromThisDb =
                    pokemonRepository.findPokemonByName(pokemon.getName());
            if (!pokemonFromThisDb.isPresent()) {
                fetchImageUrlsForPokemon(pokemon);
                pokemonRepository.save(pokemon);
            } else if (pokemonFromThisDb.get().getImageUrl_large() == null) {
                fetchImageUrlsForPokemon(pokemon);
                pokemonRepository.save(pokemon);
            }
        }
    }

    private void fetchImageUrlsForPokemon(Pokemon pokemon) {
        ExternalPokeApiResponse externalPokeApiResponse =
                externalPokeApiClient.get().uri(pokemon.getName()).retrieve().bodyToMono(ExternalPokeApiResponse.class).block();
        pokemon.setImageUrl_large(externalPokeApiResponse.getSprites().getFront_default());
        pokemon.setImageUrl_small(externalPokeApiResponse.getSprites().getOther().getOfficial_artwork().getFront_default());
    }

    // Gets called once on startup
    @CacheEvict("pokemonsInThisService")
    @Cacheable("pokemonsFetchedFromWarehouse")
    public void fetchPokemonsFromWarehouseAndSave() {
        addPokemonsToServiceDb(fetchPokemonsFromWarehouse());
    }

}
