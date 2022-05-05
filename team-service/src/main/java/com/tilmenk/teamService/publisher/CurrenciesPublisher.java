package com.tilmenk.teamService.publisher;

import com.tilmenk.teamService.config.queue.ExchangeConfig;
import com.tilmenk.teamService.config.queue.FetchCurrenciesQueueConfig;
import com.tilmenk.teamService.model.Currencies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CurrenciesPublisher {
    @Autowired
    private RabbitTemplate template;

    public Currencies publishFetchCurrencies(double costs) {
        Currencies receivedCurrencies =
                (Currencies) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, FetchCurrenciesQueueConfig.ROUTINGKEY, costs);
        return receivedCurrencies;
    }

}
