package com.tilmenk.currencyService.consumer;

import com.tilmenk.currencyService.config.FetchCurrenciesQueueConfig;
import com.tilmenk.currencyService.model.Currencies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrenciesConsumer {


    @RabbitListener(queues = FetchCurrenciesQueueConfig.QUEUE_NAME)
    public Currencies consumeAction_FetchCurrencies_forCosts(double costs) {
        log.info("Consumer: fetchCurrencies for costs: " + costs);
        try {
            return calculateCurrenciesForBasePrice(costs);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Currencies(0, 0, 0);
        }
    }

    private Currencies calculateCurrenciesForBasePrice(double price) {
        return Currencies.builder().euro(price).bitcoin(price / 36000).dollar(price * 0.7).build();
    }
}
