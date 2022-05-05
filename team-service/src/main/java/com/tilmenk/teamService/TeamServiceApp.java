package com.tilmenk.teamService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
@Slf4j
public class TeamServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(TeamServiceApp.class, args);
        log.info("team-service started !");
    }

}