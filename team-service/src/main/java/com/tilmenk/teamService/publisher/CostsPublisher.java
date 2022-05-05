package com.tilmenk.teamService.publisher;

import com.tilmenk.teamService.config.queue.ExchangeConfig;
import com.tilmenk.teamService.config.queue.FetchCostsQueueConfig;
import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CostsPublisher {
    @Autowired
    private RabbitTemplate template;

    public double publishFetchCosts(Pokemon pokemon) {
        try {
            Double costs =
                    template.convertSendAndReceiveAsType(ExchangeConfig.EXCHANGE_NAME, FetchCostsQueueConfig.ROUTINGKEY_POKEMON, pokemon, new ParameterizedTypeReference<>() {
            });
            assert costs != null;
            return costs;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }

    public double publishFetchCosts(Team team) {
        try {
            Double costs =
                    template.convertSendAndReceiveAsType(ExchangeConfig.EXCHANGE_NAME, FetchCostsQueueConfig.ROUTINGKEY_TEAM, team, new ParameterizedTypeReference<>() {

            });
            assert costs != null;
            return costs;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }

}
