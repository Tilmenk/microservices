package com.tilmenk.rabbitmq.publisher;

import com.tilmenk.rabbitmq.config.ExchangeConfig;
import com.tilmenk.rabbitmq.config.FetchPokemonQueueConfig;
import com.tilmenk.rabbitmq.model.Pokemon;
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


    public List<Pokemon> fetchPokemons() {
        log.info("Publisher - fetchPokemons");
        List<Pokemon> receivedPokemon =
                (List<Pokemon>) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, FetchPokemonQueueConfig.ROUTINGKEY, "test");
        return receivedPokemon;
    }

}
