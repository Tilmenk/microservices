package com.tilmenk.rabbitmq.publisher;

import com.tilmenk.rabbitmq.config.ExchangeConfig;
import com.tilmenk.rabbitmq.config.FetchCurrenciesQueueConfig;
import com.tilmenk.rabbitmq.responseBodies.CurrenciesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CurrenciesPublisher {

    @Autowired
    private RabbitTemplate template;

    public CurrenciesResponse publishFetchCurrencies(double costs) {
        log.info("Publisher: fetchCurrencies");
        CurrenciesResponse receivedCurrencies =
                (CurrenciesResponse) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, FetchCurrenciesQueueConfig.ROUTINGKEY, costs);
        return receivedCurrencies;
    }

}
