package com.tilmenk.costsService.consumer;

import com.tilmenk.costsService.config.FetchCostsQueueConfig;
import com.tilmenk.costsService.model.Pokemon;
import com.tilmenk.costsService.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class CostsConsumer {


    @RabbitListener(queues = FetchCostsQueueConfig.QUEUE_NAME_POKEMON)
    public double consumeAction_FetchCosts_forPokemon(Pokemon pokemon) {
        log.info("Consumer: fetchPrice");
        try {
            return (double) calculatePriceForPokemon(pokemon);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }

    @RabbitListener(queues = FetchCostsQueueConfig.QUEUE_NAME_TEAM)
    public double consumeAction_FetchCosts_forTeam(Team team) {
        log.info("Consumer: fetchPrice for team");
        try {
            return (double) calculatePriceForTeam(team);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }
    }

    private Integer calculatePriceForTeam(Team team) {
        AtomicInteger price = new AtomicInteger();
        team.getPokemon().forEach(pokemon -> price.addAndGet(calculatePriceForPokemon(pokemon)));
        return price.get();
    }

    private Integer calculatePriceForPokemon(Pokemon pokemon) {
        int tempPrice =
                (pokemon.getAttack() + pokemon.getAttack_sp() + pokemon.getDefense() + pokemon.getDefense_sp() + pokemon.getSpeed() + pokemon.getHealth()) / 6;
        if (pokemon.getLegendary()) tempPrice *= 1.5;
        return tempPrice;
    }
}
