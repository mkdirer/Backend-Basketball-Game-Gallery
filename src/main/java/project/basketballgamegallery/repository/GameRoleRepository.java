package project.basketballgamegallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.basketballgamegallery.model.GameRole;

import java.util.List;

public interface GameRoleRepository extends JpaRepository<GameRole, Long> {
    List<GameRole> findGameRolesByPlayersId(Long playerId);
    List<GameRole> findByTeamId(Long teamId);
}
