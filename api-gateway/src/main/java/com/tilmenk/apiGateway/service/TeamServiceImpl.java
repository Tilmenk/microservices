package com.tilmenk.apiGateway.service;

import com.tilmenk.apiGateway.model.Team;
import com.tilmenk.apiGateway.requestBodies.DeleteTeamBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private static final String QUEUE_URL =
            "http" + "://localhost" + ":8083" + "/api";

    private final WebClient queueClient;

    @Autowired
    public TeamServiceImpl(WebClient.Builder clientBuilder) {
        this.queueClient = clientBuilder.baseUrl(QUEUE_URL).build();
    }


    @Override
    public List<Team> getTeams() {
        return queueClient.get().uri("/team").retrieve().bodyToFlux(Team.class).collectList().block();
    }

    @Override
    public void saveTeam(Team team) throws WebClientResponseException {
        queueClient.post().uri("/team").body(BodyInserters.fromPublisher(Mono.just(team), Team.class)).retrieve().bodyToMono(String.class).block();
    }

    @Override
    public void deleteTeam(DeleteTeamBody deleteTeamBody) throws WebClientResponseException {
        queueClient.method(HttpMethod.DELETE).uri("/team").body(BodyInserters.fromPublisher(Mono.just(deleteTeamBody), DeleteTeamBody.class)).retrieve().bodyToMono(String.class).block();
    }

}
