package com.tilmenk.teamService.pokemon.exceptions;

import com.tilmenk.warehouse.pokemon.Pokemon;

public class PokemonAlreadyInDbException extends IllegalStateException {
    public PokemonAlreadyInDbException() {
        super();
    }

    public PokemonAlreadyInDbException(Pokemon pokemon) {
        super("Pokemon with name '" + pokemon.getName() + "' is already " +
                "present " + "in Pokemon table.");
    }
}
