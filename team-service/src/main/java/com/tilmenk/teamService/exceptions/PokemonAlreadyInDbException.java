package com.tilmenk.teamService.exceptions;


import com.tilmenk.teamService.model.Pokemon;

public class PokemonAlreadyInDbException extends IllegalStateException {
    public PokemonAlreadyInDbException() {
        super();
    }

    public PokemonAlreadyInDbException(Pokemon pokemon) {
        super("Pokemon with name '" + pokemon.getName() + "' is already " +
                "present " + "in Pokemon table.");
    }
}
