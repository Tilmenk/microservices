package com.tilmenk.teamService.consumer;

import com.tilmenk.teamService.config.queue.CreateTeamQueueConfig;
import com.tilmenk.teamService.config.queue.DeleteTeamQueueConfig;
import com.tilmenk.teamService.config.queue.FetchTeamQueueConfig;
import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.model.Team;
import com.tilmenk.teamService.model.TeamWithPokemonNames;
import com.tilmenk.teamService.model.apiGateway.DeleteTeamData;
import com.tilmenk.teamService.model.rabbit.RabbitResponse;
import com.tilmenk.teamService.publisher.CostsPublisher;
import com.tilmenk.teamService.publisher.CurrenciesPublisher;
import com.tilmenk.teamService.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class TeamConsumer {

    private final TeamService teamService;
    private final CostsPublisher costsPublisher;
    private final CurrenciesPublisher currenciesPublisher;


    @RabbitListener(queues = FetchTeamQueueConfig.QUEUE_NAME)
    public List<TeamWithPokemonNames> consumeAction_fetchTeams(String message) {
        List<TeamWithPokemonNames> teamsForResponse = new ArrayList<>();
        List<Team> teams = teamService.getTeams();
        for (Team team : teams) {
            double costsForTeam = costsPublisher.publishFetchCosts(team);
            team.setCosts(currenciesPublisher.publishFetchCurrencies(costsForTeam));
            TeamWithPokemonNames teamForResponse = new TeamWithPokemonNames();
            teamForResponse.setCosts(team.getCosts());
            teamForResponse.setCreator(team.getCreator());
            teamForResponse.setId(team.getId());
            teamForResponse.setName(team.getName());
            List<String> pokemonNames = new ArrayList<>();
            for (Pokemon pokemon : team.getPokemon()) {
                pokemonNames.add(pokemon.getName());
            }

            teamForResponse.setPokemon(pokemonNames);
            teamsForResponse.add(teamForResponse);
        }

        return teamsForResponse;
    }

    @RabbitListener(queues = CreateTeamQueueConfig.QUEUE_NAME)
    public RabbitResponse consumeAction_createTeam(TeamWithPokemonNames team) {
        try {
            return teamService.saveTeam(team);
        } catch (WebClientResponseException e) {
            log.error(e.getResponseBodyAsString());
            return new RabbitResponse(false, e.getResponseBodyAsString());
        }
    }

    @RabbitListener(queues = DeleteTeamQueueConfig.QUEUE_NAME)
    public RabbitResponse consumeAction_deleteTeam(DeleteTeamData deleteTeamData) {
        try {
            return teamService.deleteTeam(deleteTeamData);
        } catch (WebClientResponseException e) {
            log.error(e.getResponseBodyAsString());
            return new RabbitResponse(false, e.getResponseBodyAsString());
        }
    }


}
