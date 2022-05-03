package com.tilmenk.rabbitmq.publisher;

import com.tilmenk.rabbitmq.config.ExchangeConfig;
import com.tilmenk.rabbitmq.config.FetchCostsQueueConfig;
import com.tilmenk.rabbitmq.model.Pokemon;
import com.tilmenk.rabbitmq.model.TeamWithActualPokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CostsPublisher {


    @Autowired
    private RabbitTemplate template;


    public double publishFetchCosts(Pokemon pokemon) {
        log.info("Publisher: fetchPrice for pokemon");
        double receivedPrice = (double) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME,
                FetchCostsQueueConfig.ROUTINGKEY_POKEMON, pokemon);
        return receivedPrice;
    }

    public double publishFetchCosts(TeamWithActualPokemon team) {
        log.info("Publisher: fetchPrice for team");
        double receivedPrice = (double) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME,
                FetchCostsQueueConfig.ROUTINGKEY_TEAM, team);
        return receivedPrice;
    }

}
