package project.basketballgamegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.basketballgamegallery.model.GameRole;
import project.basketballgamegallery.model.Player;
import project.basketballgamegallery.repository.TeamRepository;
import project.basketballgamegallery.repository.GameRoleRepository;
import project.basketballgamegallery.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class GameRoleController {
    @Autowired
    GameRoleRepository gameRoleRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/gameroles")
    public ResponseEntity<List<GameRole>> getAllGameRole() {
        List<GameRole> gameRoles = new ArrayList<GameRole>();
        gameRoleRepository.findAll().forEach(gameRoles::add);

        if (gameRoles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gameRoles, HttpStatus.OK);
    }
    @GetMapping("/gameroles/{id}")
    public ResponseEntity<GameRole> getGameRoleById(@PathVariable("id") long id) {
        GameRole gameRole = gameRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found GameRole with id = " + id));
        return new ResponseEntity<>(gameRole, HttpStatus.OK);
    }
    @GetMapping("/teams/{clubId}/gameroles")
    public ResponseEntity<List<GameRole>> getAllPlayersByClubId(@PathVariable(value = "clubId") Long clubId) {
        if (!teamRepository.existsById(clubId)) {
            throw new RuntimeException("Not found Club with id = " + clubId);
        }
        List<GameRole> gameRoles = gameRoleRepository.findByTeamId(clubId);
        return new ResponseEntity<>(gameRoles, HttpStatus.OK);
    }
    @PostMapping("/teams/{clubId}/gamerole")
    public ResponseEntity<GameRole> createGameRole(@PathVariable(value = "clubId") Long clubId,
                                                   @RequestBody GameRole gameRoleRequest) {
        GameRole gameRole = teamRepository.findById(clubId).map(club -> {
            gameRoleRequest.setTeam(club);
            return gameRoleRepository.save(gameRoleRequest);
        }).orElseThrow(() -> new RuntimeException("Not found Club with id = " + clubId));
        return new ResponseEntity<>(gameRole, HttpStatus.CREATED);
    }
    @PutMapping("/gameroles/{id}")
    public ResponseEntity<GameRole> updateGameRole(@PathVariable("id") long id, @RequestBody GameRole gameRole) {
        GameRole _gameRole = gameRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found GameRole with id = " + id));
        _gameRole.setPoints(gameRole.getPoints());

        return new ResponseEntity<>(gameRoleRepository.save(_gameRole), HttpStatus.OK);
    }
    @DeleteMapping("/gameroles/{id}")
    public ResponseEntity<HttpStatus> deleteGameRole(@PathVariable("id") long id) {
        gameRoleRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/gameroles")
    public ResponseEntity<HttpStatus> deleteAllGameRoles() {
        gameRoleRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/gameroles/{gameRoleId}/players")
    public ResponseEntity<List<Player>> getAllPlayersByGameRoleId(@PathVariable(value = "gameRoleId") Long gameRoleId) {
        if (!gameRoleRepository.existsById(gameRoleId)) {
            throw new RuntimeException("Not found Tutorial with id = " + gameRoleId);
        }
        List<Player> players = playerRepository.findPlayersByGameRolesId(gameRoleId);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    @GetMapping("/players/{playerId}/gameroles")
    public ResponseEntity<List<GameRole>> getAllGameRolesByPlayerId(@PathVariable(value = "playerId") Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new RuntimeException("Not found Tutorial with id = " + playerId);
        }
        List<GameRole> gameRoles = gameRoleRepository.findGameRolesByPlayersId(playerId);
        return new ResponseEntity<>(gameRoles, HttpStatus.OK);
    }
    @PostMapping("/gameroles/{gameRoleId}/player")
    public ResponseEntity<Player> addPlayer(@PathVariable(value = "gameRoleId") Long gameRoleId, @RequestBody Player playerRequest) {
        Player tag = gameRoleRepository.findById(gameRoleId).map(gameRole -> {
            long playerId = playerRequest.getId();

            // playerId is existed
            if (playerId != 0L) {
                Player _player = playerRepository.findById(playerId)
                        .orElseThrow(() -> new RuntimeException("Not found Tag with id = " + playerId));
                gameRole.addPlayer(_player);
                gameRoleRepository.save(gameRole);
                return _player;
            }

            // add and create new Tag
            gameRole.addPlayer(playerRequest);
            return playerRepository.save(playerRequest);
        }).orElseThrow(() -> new RuntimeException("Not found Tutorial with id = " + gameRoleId));
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }
}
