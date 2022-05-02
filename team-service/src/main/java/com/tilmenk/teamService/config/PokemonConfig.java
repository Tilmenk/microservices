package com.tilmenk.teamService.config;


import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.repository.PokemonRepository;
import com.tilmenk.teamService.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;


@Configuration
public class PokemonConfig {
    @Autowired
    private Environment env;

    @Bean
    CommandLineRunner commandLineRunnerPokemon(PokemonRepository repository,
                                               PokemonService pokemonService) {
        return args -> {
                    pokemonService.fetchPokemonsFromWarehouseAndSave();
          /*  if (Objects.equals(env.getProperty("DEPLOYMENT_ENV"), "dev")) {
                repository.saveAll(List.of(pikachu, bulbasaur));
            }*/
        };
    }
}