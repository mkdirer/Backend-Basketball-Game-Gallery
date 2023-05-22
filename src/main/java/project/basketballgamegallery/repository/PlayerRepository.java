package project.basketballgamegallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.basketballgamegallery.model.Player;

import javax.transaction.Transactional;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeamId(Long postId);
    List<Player> findPlayersByGameRolesId(Long gameRoleId);

    @Transactional
    void deleteByTeamId(long teamId);
}