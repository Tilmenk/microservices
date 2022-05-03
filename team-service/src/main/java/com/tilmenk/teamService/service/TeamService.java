package com.tilmenk.teamService.service;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.model.Team;
import com.tilmenk.teamService.repository.PokemonRepository;
import com.tilmenk.teamService.repository.TeamRepository;
import com.tilmenk.teamService.requestBodies.CreateTeamBody;
import com.tilmenk.teamService.requestBodies.DeleteTeamBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeamService {
    private static final String WAREHOUSE_URL_TEAM = "http" + "://localhost:8080/api/team";
    private final TeamRepository teamRepository;
    private final PokemonRepository pokemonRepository;
    private WebClient wareHouseApiClient = null;


    @Autowired
    public TeamService(TeamRepository teamRepository, PokemonRepository pokemonRepository,
                       WebClient.Builder wareHouseApiClientBuilder) {
        this.teamRepository = teamRepository;
        this.wareHouseApiClient = wareHouseApiClientBuilder.baseUrl(WAREHOUSE_URL_TEAM).build();
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

    // called on startUp appends teams with same id, overwrites teams with duplicate name
    private void addTeamsToServiceDb(List<Team> teamToAdd) {
        for (Team team : teamToAdd) {
            team.setCreator("default");
            Optional<Team> teamWithSameIdInThisDb = teamRepository.findTeamById(team.getId());
            if (teamWithSameIdInThisDb.isPresent()) {
                Optional<Team> teamWithSameNameAndCreator = teamRepository.findTeamByNameAndCreator(team.getName(),
                        team.getCreator());
                if (teamWithSameNameAndCreator.isPresent()) {
                    team.setId(teamWithSameNameAndCreator.get().getId());
                    teamRepository.save(team);
                } else {
                    Team teamCopyWithNewId = new Team(team.getPokemon(), team.getName(), team.getCreator());
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
    public ResponseEntity createTeam(CreateTeamBody createTeamBody) {
        if (createTeamBody.getPokemon().size() < 6) {
            return ResponseEntity.internalServerError().body("6 Pokemon needed.");
        }
        if (teamRepository.findTeamByNameAndCreator(createTeamBody.getName(), createTeamBody.getCreator()).isPresent()) {
            return ResponseEntity.internalServerError().body("Team name not unique for creator.");
        }

        List<Pokemon> pokemonsInTeam = new ArrayList<Pokemon>();
        //pokemonRepository
        for (String pokeName : createTeamBody.getPokemon()) {
            if (pokemonsInTeam.size() > 5) {
                break;
            }
            Optional<Pokemon> pokemonToAdd = pokemonRepository.findPokemonByName(pokeName);
            if (pokemonToAdd.isEmpty()) {
                return ResponseEntity.internalServerError().body("Pokemon: " + pokeName + " " + "not found.");
            } else {
                pokemonsInTeam.add(pokemonToAdd.get());
            }
        }
        Team teamToCreate =
                Team.builder().creator(createTeamBody.getCreator()).name(createTeamBody.getName()).pokemon(pokemonsInTeam).build();
        teamRepository.save(teamToCreate);
        return ResponseEntity.ok().body("Team created.");
    }

    public ResponseEntity deleteTeam(DeleteTeamBody deleteTeamBody) {
        Optional<Team> teamToDelete = teamRepository.findTeamById(deleteTeamBody.getTeamId());
        if (teamToDelete.isPresent()) {
            if (teamToDelete.get().getCreator().equals(deleteTeamBody.getUserThatIsDeleting())) {
                teamRepository.delete(teamToDelete.get());
                return ResponseEntity.ok().body("Team deleted");
            } else {
                return ResponseEntity.internalServerError().body("user is not creator of team.");
            }
        } else {
            return ResponseEntity.internalServerError().body("team with teamId not found.");
        }
    }
}
