package project.basketballgamegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.basketballgamegallery.model.Player;
import project.basketballgamegallery.repository.TeamRepository;
import project.basketballgamegallery.repository.PlayerRepository;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PlayerController {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @GetMapping("/teams/{teamId}/players")
    public ResponseEntity<List<Player>> getAllPlayersByTeamId(@PathVariable(value = "teamId") Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Not found Team with id = " + teamId);
        }
        List<Player> players = playerRepository.findByTeamId(teamId);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    @GetMapping("/players2/{id}")
    public ResponseEntity<Player> getPlayersByTeamId(@PathVariable(value = "id") Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found Player with id = " + id));
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
    @PostMapping("/teams/{teamId}/player")
    public ResponseEntity<Player> createPlayer(@PathVariable(value = "teamId") Long teamId,
                                                 @RequestBody Player playerRequest) {
        Player player = teamRepository.findById(teamId).map(team -> {
            playerRequest.setTeam(team);
            return playerRepository.save(playerRequest);
        }).orElseThrow(() -> new RuntimeException("Not found Team with id = " + teamId));
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }
    @PutMapping("/player2/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id, @RequestBody Player playerRequest) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PlayerId " + id + "not found"));
        player.setFirstName(playerRequest.getFirstName());
        player.setLastName(playerRequest.getLastName());
        player.setAge(playerRequest.getAge());
        return new ResponseEntity<>(playerRepository.save(player), HttpStatus.OK);
    }
    @DeleteMapping("/player2/{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("id") long id) {
        playerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/teams/{teamId}/player")
    public ResponseEntity<List<Player>> deleteAllPlayersOfTeam(@PathVariable(value = "teamId") Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Not found Team with id = " + teamId);
        }
        playerRepository.deleteByTeamId(teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
