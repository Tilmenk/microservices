package com.tilmenk.teamService.config;


import com.tilmenk.teamService.repository.TeamRepository;
import com.tilmenk.teamService.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class TeamConfig {
    @Autowired
    private Environment env;

    @Bean
    CommandLineRunner commandLineRunnerTeam(TeamRepository repository,
                                               TeamService teamService) {
        return args -> {
                    teamService.fetchTeamsFromWarehouseAndSave();
          /*  if (Objects.equals(env.getProperty("DEPLOYMENT_ENV"), "dev")) {
                repository.saveAll(List.of(pikachu, bulbasaur));
            }*/
        };
    }
}