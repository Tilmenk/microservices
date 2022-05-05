package com.tilmenk.apiGateway.publisher;

import com.tilmenk.apiGateway.config.ExchangeConfig;
import com.tilmenk.apiGateway.config.FetchPokemonQueueConfig;
import com.tilmenk.apiGateway.model.teamService.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class PokemonPublisher {
    @Autowired
    private RabbitTemplate template;

    public List<Pokemon> publishFetchPokemons() {
        List<Pokemon> receivedPokemon =
                (List<Pokemon>) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, FetchPokemonQueueConfig.ROUTINGKEY, "give me pokemon!");
        return receivedPokemon;
    }

}
