package com.tilmenk.apiGateway.service;

import com.tilmenk.apiGateway.model.Pokemon;

import java.util.List;

public interface PokemonService {

    List<Pokemon> getPokemons();
}
