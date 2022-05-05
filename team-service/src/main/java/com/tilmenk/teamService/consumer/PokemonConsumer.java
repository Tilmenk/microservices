package com.tilmenk.teamService.consumer;

import com.tilmenk.teamService.config.queue.FetchPokemonQueueConfig;
import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.publisher.CostsPublisher;
import com.tilmenk.teamService.publisher.CurrenciesPublisher;
import com.tilmenk.teamService.service.PokemonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class PokemonConsumer {

    private final PokemonService pokemonService;
    private final CostsPublisher pricePublisher;
    private final CurrenciesPublisher currenciesPublisher;


    @RabbitListener(queues = FetchPokemonQueueConfig.QUEUE_NAME)
    public List<Pokemon> consumeAction_fetchPokemons(String message) {
        List<Pokemon> pokemons = pokemonService.getPokemons();
        System.out.println(pokemons);
        for (Pokemon pokemon : pokemons) {
            double costsForPokemon = pricePublisher.publishFetchCosts(pokemon);
            pokemon.setCosts(currenciesPublisher.publishFetchCurrencies(costsForPokemon));
        }
        return pokemons;
    }
}
