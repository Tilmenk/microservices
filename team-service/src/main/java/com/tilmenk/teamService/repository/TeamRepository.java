package com.tilmenk.teamService.repository;

import com.tilmenk.teamService.model.Pokemon;
import com.tilmenk.teamService.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    //SELECT * FROM student WHERE email = email
    //@Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Team> findTeamById(Long id);
    Optional<Team> findTeamByNameAndCreator(String name, String creator);

}
