package com.tilmenk.rabbitmq.consumer;

import com.tilmenk.rabbitmq.config.CreateTeamQueueConfig;
import com.tilmenk.rabbitmq.config.DeleteTeamQueueConfig;
import com.tilmenk.rabbitmq.config.FetchTeamQueueConfig;
import com.tilmenk.rabbitmq.model.RabbitResponse;
import com.tilmenk.rabbitmq.model.TeamWithActualPokemon;
import com.tilmenk.rabbitmq.model.TeamWithPokemonNames;
import com.tilmenk.rabbitmq.publisher.CostsPublisher;
import com.tilmenk.rabbitmq.publisher.CurrenciesPublisher;
import com.tilmenk.rabbitmq.publisher.TeamPublisher;
import com.tilmenk.rabbitmq.requestBodies.DeleteTeamBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class TeamConsumer {


    private static final String TEAMSERVICE_URL = "http" + "://localhost:8081"
            + "/api/team";
    private final WebClient teamServiceClient;

    private final TeamPublisher teamPublisher;
    private final CostsPublisher pricePublisher;
    private final CurrenciesPublisher currenciesPublisher;


    @Autowired
    public TeamConsumer(WebClient.Builder clientBuilder,
                        TeamPublisher teamPublisher,
                        CostsPublisher pricePublisher,
                        CurrenciesPublisher currenciesPublisher) {
        this.teamServiceClient = clientBuilder.baseUrl(TEAMSERVICE_URL).build();
        this.teamPublisher = teamPublisher;
        this.pricePublisher = pricePublisher;
        this.currenciesPublisher = currenciesPublisher;
    }

    @RabbitListener(queues = FetchTeamQueueConfig.QUEUE_NAME)
    public List<TeamWithActualPokemon> consumeAction_fetchTeams(String message) {
        log.info("Consumer - consumeAction_fetchTeams");
        List<TeamWithActualPokemon> teamsFromTeamService =
                teamServiceClient.get().uri("/").retrieve().bodyToFlux(TeamWithActualPokemon.class).collectList().block();
        assert teamsFromTeamService != null;
        for (TeamWithActualPokemon team : teamsFromTeamService) {
            double costsForTeam = pricePublisher.publishFetchCosts(team);
            team.setCosts(currenciesPublisher.publishFetchCurrencies(costsForTeam));
        }
        return teamsFromTeamService;
    }

    @RabbitListener(queues = CreateTeamQueueConfig.QUEUE_NAME)
    public RabbitResponse consumeAction_createTeam(TeamWithPokemonNames team) {
        try {
            System.out.println("test .. ");
            String response =
                    teamServiceClient.post().uri("/").body(BodyInserters.fromPublisher(Mono.just(team), TeamWithPokemonNames.class)).retrieve().bodyToMono(String.class).block();
            return new RabbitResponse(true, response);
        } catch (WebClientResponseException e) {
            log.error(e.getResponseBodyAsString());
            return new RabbitResponse(false, e.getResponseBodyAsString());
        }
    }

    @RabbitListener(queues = DeleteTeamQueueConfig.QUEUE_NAME)
    public RabbitResponse consumeAction_deleteTeam(DeleteTeamBody deleteTeamBody) {
        try {
            log.info("sending delete request!");
            String response =
                    teamServiceClient.method(HttpMethod.DELETE).uri("/").body(BodyInserters.fromPublisher(Mono.just(deleteTeamBody), DeleteTeamBody.class)).retrieve().bodyToMono(String.class).block();
            return new RabbitResponse(true, response);
        } catch (WebClientResponseException e) {
            log.error("Logging error: {}", e.getMessage());
            return new RabbitResponse(false, e.getResponseBodyAsString());
        }
    }


}
