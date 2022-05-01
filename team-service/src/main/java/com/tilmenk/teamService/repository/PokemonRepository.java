package com.tilmenk.teamService.repository;

import com.tilmenk.teamService.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, String> {
    //SELECT * FROM student WHERE email = email
    //@Query("SELECT s FROM Student s WHERE s.email = ?1")

    Optional<Pokemon> findPokemonByName(String name);
}
