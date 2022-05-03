package com.tilmenk.rabbitmq.consumer;

import com.tilmenk.rabbitmq.config.FetchCostsQueueConfig;
import com.tilmenk.rabbitmq.model.Pokemon;
import com.tilmenk.rabbitmq.model.TeamWithActualPokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CostsConsumer {

    private static final String PRICESERVICE_URL = "http" + "://localhost" + ":8082/api/price";
    private final WebClient priceServiceClient;

    @Autowired
    public CostsConsumer(WebClient.Builder clientBuilder) {
        this.priceServiceClient = clientBuilder.baseUrl(PRICESERVICE_URL).build();
    }

    @RabbitListener(queues = FetchCostsQueueConfig.QUEUE_NAME_POKEMON)
    public double consumeAction_FetchCosts_forPokemon(Pokemon pokemon) {
        log.info("Consumer: fetchPrice");
        try {
            double price =
                    priceServiceClient.post().uri("/pokemon").body(BodyInserters.fromPublisher(Mono.just(pokemon),
                            Pokemon.class)).retrieve().bodyToMono(double.class).block();
            log.info("Consumer: Price received! " + price);
            return price;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }

    @RabbitListener(queues = FetchCostsQueueConfig.QUEUE_NAME_TEAM)
    public double consumeAction_FetchCosts_forTeam(TeamWithActualPokemon team) {
        log.info("Consumer: fetchPrice for team");
        try {
            double price = priceServiceClient.post().uri("/team").body(BodyInserters.fromPublisher(Mono.just(team),
                    TeamWithActualPokemon.class)).retrieve().bodyToMono(double.class).block();
            log.info("Consumer: Price received! " + price);
            return price;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }
}
