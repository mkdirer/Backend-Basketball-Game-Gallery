package project.basketballgamegallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.basketballgamegallery.model.Team;

import java.util.List;
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByNameContaining(String name);
    List<Team> findByLeagueContaining(String league);
}
