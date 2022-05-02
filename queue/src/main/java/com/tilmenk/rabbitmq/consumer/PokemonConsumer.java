package com.tilmenk.rabbitmq.consumer;

import com.tilmenk.rabbitmq.config.FetchPokemonQueueConfig;
import com.tilmenk.rabbitmq.model.Pokemon;
import com.tilmenk.rabbitmq.publisher.CurrenciesPublisher;
import com.tilmenk.rabbitmq.publisher.PricePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
public class PokemonConsumer {


    private static final String TEAMSERVICE_URL = "http" + "://localhost:8081"
            + "/api/pokemon";
    private final WebClient teamServiceClient;

    private final PricePublisher pricePublisher;
    private final CurrenciesPublisher currenciesPublisher;


    @Autowired
    public PokemonConsumer(WebClient.Builder clientBuilder,
                           PricePublisher pricePublisher,
                           CurrenciesPublisher currenciesPublisher) {
        this.teamServiceClient = clientBuilder.baseUrl(TEAMSERVICE_URL).build();
        this.pricePublisher = pricePublisher;
        this.currenciesPublisher = currenciesPublisher;
    }

    @RabbitListener(queues = FetchPokemonQueueConfig.QUEUE_NAME)
    public List<Pokemon> consumeAction_fetchPokemons(String message) {
        log.info("Consumer - consumeAction_fetchPokemons");
        List<Pokemon> pokemonsFromTeamService = teamServiceClient.get().uri(
                "/").retrieve().bodyToFlux(Pokemon.class).collectList().block();
        assert pokemonsFromTeamService != null;
        for (Pokemon pokemon : pokemonsFromTeamService) {
            double costsForPokemon = pricePublisher.publishFetchPrice(pokemon);
            pokemon.setCosts(currenciesPublisher.publishFetchCurrencies(costsForPokemon));
        }
        return pokemonsFromTeamService;
    }
}
