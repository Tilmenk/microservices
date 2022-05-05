package com.tilmenk.apiGateway.publisher;

import com.tilmenk.apiGateway.config.CreateTeamQueueConfig;
import com.tilmenk.apiGateway.config.DeleteTeamQueueConfig;
import com.tilmenk.apiGateway.config.ExchangeConfig;
import com.tilmenk.apiGateway.config.FetchTeamQueueConfig;
import com.tilmenk.apiGateway.model.rabbit.RabbitResponse;
import com.tilmenk.apiGateway.model.teamService.DeleteTeamData;
import com.tilmenk.apiGateway.model.teamService.TeamWithPokemonNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class TeamPublisher {
    @Autowired
    private RabbitTemplate template;

    public List<TeamWithPokemonNames> publishFetchTeams() {
        try {
            List<TeamWithPokemonNames> teams =
                    template.convertSendAndReceiveAsType(ExchangeConfig.EXCHANGE_NAME, FetchTeamQueueConfig.ROUTINGKEY, "give me teams!", new ParameterizedTypeReference<>() {
            });
            assert teams != null;
            return teams;
        } catch (ListenerExecutionFailedException e) {
            log.error(e.getMessage());
            return List.of();
        }
    }

    public RabbitResponse publishSaveTeam(TeamWithPokemonNames team) {
        log.info("Publisher - createTeam");
        return (RabbitResponse) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, CreateTeamQueueConfig.ROUTINGKEY, team);
    }


    public RabbitResponse publishDeleteTeam(DeleteTeamData deleteTeamBody) {
        log.info("Publisher - createTeam");
        return (RabbitResponse) template.convertSendAndReceive(ExchangeConfig.EXCHANGE_NAME, DeleteTeamQueueConfig.ROUTINGKEY, deleteTeamBody);

    }
}
