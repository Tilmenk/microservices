package com.tilmenk.rabbitmq.consumer;

import com.tilmenk.rabbitmq.config.FetchPriceQueueConfig;
import com.tilmenk.rabbitmq.model.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PriceConsumer {

    private static final String PRICESERVICE_URL = "http" + "://localhost" +
            ":8082/api/price/pokemon";
    private final WebClient priceServiceClient;

    @Autowired
    public PriceConsumer(WebClient.Builder clientBuilder) {
        this.priceServiceClient =
                clientBuilder.baseUrl(PRICESERVICE_URL).build();
    }

    @RabbitListener(queues = FetchPriceQueueConfig.QUEUE_NAME)
    public double consumeAction_FetchCosts_forPokemon(Pokemon pokemon) {
        log.info("Consumer: fetchPrice");
        try {
            double price =
                    priceServiceClient.post().uri("/").body(BodyInserters.fromPublisher(Mono.just(pokemon), Pokemon.class)).retrieve().bodyToMono(double.class).block();
            log.info("Consumer: Price received! " + price);
            return price;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }
}
