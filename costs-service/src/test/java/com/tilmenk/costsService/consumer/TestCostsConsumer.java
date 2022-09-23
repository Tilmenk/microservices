package com.tilmenk.costsService.consumer;

import com.tilmenk.costsService.model.Pokemon;
import com.tilmenk.costsService.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestCostsConsumer {
    private CostsConsumer costsConsumer;
    Pokemon pokemon1Mock;
    Pokemon pokemon2Mock;
    Team teamMock1;
    Team teamMock2;
    @BeforeEach
    void setUp() {
        costsConsumer = new CostsConsumer();
        this.teamMock1 = Mockito.mock(Team.class);
        this.teamMock2 = Mockito.mock(Team.class);
        this.pokemon1Mock = Mockito.mock(Pokemon.class);
        this.pokemon2Mock = Mockito.mock(Pokemon.class);
        List<Pokemon> pokemonList = new LinkedList<>();
        pokemonList.add(pokemon1Mock);
        pokemonList.add(pokemon2Mock);
        Mockito.when(teamMock1.getPokemon()).thenReturn(pokemonList);
        Mockito.when(teamMock2.getPokemon()).thenReturn(new LinkedList<>());
        Mockito.when(pokemon1Mock.getAttack()).thenReturn(10);
        Mockito.when(pokemon1Mock.getDefense()).thenReturn(10);
        Mockito.when(pokemon1Mock.getAttack_sp()).thenReturn(10);
        Mockito.when(pokemon1Mock.getHealth()).thenReturn(10);
        Mockito.when(pokemon1Mock.getDefense_sp()).thenReturn(10);
        Mockito.when(pokemon1Mock.getSpeed()).thenReturn(10);
        Mockito.when(pokemon1Mock.getLegendary()).thenReturn(false);
        Mockito.when(pokemon2Mock.getAttack()).thenReturn(10);
        Mockito.when(pokemon2Mock.getDefense()).thenReturn(10);
        Mockito.when(pokemon2Mock.getAttack_sp()).thenReturn(10);
        Mockito.when(pokemon2Mock.getHealth()).thenReturn(10);
        Mockito.when(pokemon2Mock.getDefense_sp()).thenReturn(10);
        Mockito.when(pokemon2Mock.getSpeed()).thenReturn(10);
        Mockito.when(pokemon2Mock.getLegendary()).thenReturn(true);
    }

    @Test
    void consumeAction_FetchCosts_forPokemon() {
        //GIVEN
        double expected = 10;
        //WHEN
        double res = costsConsumer.consumeAction_FetchCosts_forPokemon(pokemon1Mock);
        //THEN
        assertEquals(expected, res);
    }

    @Test
    void consumeAction_FetchCosts_forPokemonNull() {
        //GIVEN
        Pokemon nullPokemon = null;
        double expected = 0;
        //WHEN
        double res = costsConsumer.consumeAction_FetchCosts_forPokemon(nullPokemon);
        //THEN
        assertEquals(expected, res);
    }

    @Test
    void consumeAction_FetchCosts_forPokemon_Legendary() {
        //GIVEN
        double expected = 15.0;
        //WHEN
        double res = costsConsumer.consumeAction_FetchCosts_forPokemon(pokemon2Mock);
        //THEN
        assertEquals(expected, res);
    }

    @Test
    void consumeAction_FetchCosts_forTeam() {
        //GIVEN
        double expected = 25;
        //WHEN
        double res = costsConsumer.consumeAction_FetchCosts_forTeam(teamMock1);
        //THEN
        assertEquals(expected, res);
    }

    @Test
    void consumeAction_FetchCosts_forTeam_EmptyTeam() {
        //GIVEN
        double expected = 0;
        //WHEN
        double res = costsConsumer.consumeAction_FetchCosts_forTeam(teamMock2);
        //THEN
        assertEquals(expected, res);
    }
}