package com.tilmenk.teamService;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.reactive.function.client.WebClient;


public class TestMain {
    private static final WebClient externalPokeApiClient =  WebClient.create("https://pokeapi" +
            ".co/api/v2/");

    public static void main(String[] args) throws InterruptedException {
        System.out.println("test");
        Thread.sleep(1000);
        System.out.println(externalPokeApiClient.get().uri("/pokemon/" +
                "pikachu").retrieve().bodyToMono(JsonNode.class).map(s -> s.path("Sprites")).block());

    }

}