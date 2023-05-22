package project.basketballgamegallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.basketballgamegallery.model.Match;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeTeamContaining(String club);
    List<Match> findByVisitTeamContaining(String club);
}
