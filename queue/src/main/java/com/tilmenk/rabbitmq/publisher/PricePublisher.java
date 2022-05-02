package com.tilmenk.rabbitmq.publisher;

import com.tilmenk.rabbitmq.config.ExchangeConfig;
import com.tilmenk.rabbitmq.config.FetchPriceQueueConfig;
import com.tilmenk.rabbitmq.model.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PricePublisher {


    @Autowired
    private RabbitTemplate template;


    public double publishFetchPrice(Pokemon pokemon) {
        log.info("Publisher: fetchPrice");
        double receivedPrice =
                (double) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, FetchPriceQueueConfig.ROUTINGKEY, pokemon);
        return receivedPrice;
    }

}
