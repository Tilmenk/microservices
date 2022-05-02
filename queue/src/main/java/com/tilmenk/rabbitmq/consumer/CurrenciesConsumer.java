package com.tilmenk.rabbitmq.consumer;

import com.tilmenk.rabbitmq.config.FetchCurrenciesQueueConfig;
import com.tilmenk.rabbitmq.responseBodies.CurrenciesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class CurrenciesConsumer {

    private static final String PRICESERVICE_URL = "http" + "://localhost" + ":8085/api" + "/currency";
    private final WebClient priceServiceClient;

    @Autowired
    public CurrenciesConsumer(WebClient.Builder clientBuilder) {
        this.priceServiceClient = clientBuilder.baseUrl(PRICESERVICE_URL).build();
    }

    @RabbitListener(queues = FetchCurrenciesQueueConfig.QUEUE_NAME)
    public CurrenciesResponse consumeAction_FetchCurrencies_forCosts(double costs) {
        log.info("Consumer: fetchCurrencies for costs: " + costs);
        try {
            CurrenciesResponse currenciesResponse =
                    priceServiceClient.get().uri("/" + costs).retrieve().bodyToMono(CurrenciesResponse.class).block();
            log.info("Consumer: Currencies received! " + currenciesResponse);
            return currenciesResponse;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new CurrenciesResponse(0, 0, 0);
        }
    }
}
