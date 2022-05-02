package com.tilmenk.teamService.repository;

import com.tilmenk.teamService.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, String> {
    Optional<Pokemon> findPokemonByName(String name);
}
