package com.tilmenk.teamService.service;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.model.Team;
import com.tilmenk.teamService.model.TeamWithPokemonNames;
import com.tilmenk.teamService.model.apiGateway.DeleteTeamData;
import com.tilmenk.teamService.model.rabbit.RabbitResponse;
import com.tilmenk.teamService.repository.PokemonRepository;
import com.tilmenk.teamService.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeamService {
    private static final String WAREHOUSE_URL_TEAM =
            "http" + "://localhost" + ":8080/api/team";
    private final TeamRepository teamRepository;
    private final PokemonRepository pokemonRepository;
    private WebClient wareHouseApiClient = null;


    @Autowired
    public TeamService(TeamRepository teamRepository,
                       PokemonRepository pokemonRepository,
                       WebClient.Builder wareHouseApiClientBuilder) {
        this.teamRepository = teamRepository;
        this.wareHouseApiClient =
                wareHouseApiClientBuilder.baseUrl(WAREHOUSE_URL_TEAM).build();
        this.pokemonRepository = pokemonRepository;
    }


    @Cacheable("teamsInThisService")
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    // warehouse
    private List<Team> fetchTeamsFromWarehouse() {
        try {
            return wareHouseApiClient.get().uri("/").retrieve().bodyToFlux(Team.class).collectList().block();
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return List.of();
    }

    // called on startUp appends teams with same id, overwrites teams with
    // duplicate name
    private void addTeamsToServiceDb(List<Team> teamToAdd) {
        for (Team team : teamToAdd) {
            team.setCreator("default");
            Optional<Team> teamWithSameIdInThisDb =
                    teamRepository.findTeamById(team.getId());
            if (teamWithSameIdInThisDb.isPresent()) {
                Optional<Team> teamWithSameNameAndCreator =
                        teamRepository.findTeamByNameAndCreator(team.getName(), team.getCreator());
                if (teamWithSameNameAndCreator.isPresent()) {
                    team.setId(teamWithSameNameAndCreator.get().getId());
                    teamRepository.save(team);
                } else {
                    Team teamCopyWithNewId = new Team(team.getPokemon(),
                            team.getName(), team.getCreator());
                    teamRepository.save(teamCopyWithNewId);
                }

            } else {
                teamRepository.save(team);
            }
        }
    }


    // Gets called once on startup
    @CacheEvict("teamsInThisService")
    @Cacheable("teamsFetchedFromWarehouse")
    public void fetchTeamsFromWarehouseAndSave() {
        addTeamsToServiceDb(fetchTeamsFromWarehouse());
    }

    // used by controller to add custom teams from users
    @CacheEvict("teamsInThisService")
    public RabbitResponse saveTeam(TeamWithPokemonNames teamWithPokemonNames) {
        if (teamWithPokemonNames.getPokemon().size() < 6) {
            return new RabbitResponse(false, "6 Pokemon " + "needed.");
        }
        if (teamRepository.findTeamByNameAndCreator(teamWithPokemonNames.getName(), teamWithPokemonNames.getCreator()).isPresent()) {
            return new RabbitResponse(false, "Team name not unique for " +
                    "creator.");
        }

        List<Pokemon> pokemonsInTeam = new ArrayList<Pokemon>();
        //pokemonRepository
        for (String pokeName : teamWithPokemonNames.getPokemon()) {
            if (pokemonsInTeam.size() > 5) {
                break;
            }
            Optional<Pokemon> pokemonToAdd =
                    pokemonRepository.findPokemonByName(pokeName);
            if (pokemonToAdd.isEmpty()) {
                return new RabbitResponse(false,
                        "Pokemon: " + pokeName + " " + "not found.");
            } else {
                pokemonsInTeam.add(pokemonToAdd.get());
            }
        }
        Team teamToCreate =
                Team.builder().creator(teamWithPokemonNames.getCreator()).name(teamWithPokemonNames.getName()).pokemon(pokemonsInTeam).build();
        teamRepository.save(teamToCreate);
        return new RabbitResponse(true, "Team created");
    }

    public RabbitResponse deleteTeam(DeleteTeamData deleteTeamBody) {
        Optional<Team> teamToDelete =
                teamRepository.findTeamById(deleteTeamBody.getTeamId());
        if (teamToDelete.isPresent()) {
            if (teamToDelete.get().getCreator().equals(deleteTeamBody.getUserThatIsDeleting())) {
                teamRepository.delete(teamToDelete.get());
                return new RabbitResponse(true, "Team deleted");
            } else {
                return new RabbitResponse(false, "user is " + "not" + " " +
                        "creator of team");
            }
        } else {
            return new RabbitResponse(false, "team with teamId not found");
        }
    }
}
