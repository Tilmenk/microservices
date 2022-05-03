package com.tilmenk.rabbitmq.publisher;

import com.tilmenk.rabbitmq.config.CreateTeamQueueConfig;
import com.tilmenk.rabbitmq.config.DeleteTeamQueueConfig;
import com.tilmenk.rabbitmq.config.ExchangeConfig;
import com.tilmenk.rabbitmq.config.FetchTeamQueueConfig;
import com.tilmenk.rabbitmq.model.RabbitResponse;
import com.tilmenk.rabbitmq.model.TeamWithActualPokemon;
import com.tilmenk.rabbitmq.model.TeamWithPokemonNames;
import com.tilmenk.rabbitmq.requestBodies.DeleteTeamBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class TeamPublisher {

    @Autowired
    private RabbitTemplate template;

    public List<TeamWithActualPokemon> publishFetchTeams() {
        log.info("Publisher - fetchTeams");
        try {
            List<TeamWithActualPokemon> receivedTeams =
                    (List<TeamWithActualPokemon>) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, FetchTeamQueueConfig.ROUTINGKEY, "test");
            return receivedTeams;
        } catch (ListenerExecutionFailedException e) {
            log.error(e.getMessage());
            return List.of();
        }
    }

    public ResponseEntity<Object> publishCreateTeam(TeamWithPokemonNames team) {
        log.info("Publisher - createTeam");
        RabbitResponse response =
                (RabbitResponse) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, CreateTeamQueueConfig.ROUTINGKEY, team);
        return response.success() ?
                ResponseEntity.ok().body(response.message()) :
                ResponseEntity.internalServerError().body(response.message());
    }

    public ResponseEntity<Object> publishDeleteTeam(DeleteTeamBody deleteTeamBody) {
        log.info("Publisher - createTeam");
        RabbitResponse response =
                (RabbitResponse) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, DeleteTeamQueueConfig.ROUTINGKEY, deleteTeamBody);
        return response.success() ?
                ResponseEntity.ok().body(response.message()) :
                ResponseEntity.internalServerError().body(response.message());
    }
}
