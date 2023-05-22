package project.basketballgamegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.basketballgamegallery.model.Match;
import project.basketballgamegallery.repository.TeamRepository;
import project.basketballgamegallery.repository.GameRoleRepository;
import project.basketballgamegallery.repository.MatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Klasa MatchController obslugujaca endpointy dotyczace meczy koszykarskich
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MatchController {
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    GameRoleRepository gameRoleRepository;
    @Autowired
    TeamRepository teamRepository;

    /**
     * Zwraca wszystkie mecze lub takie w ktorych wystapil podana drużyna
     * @param team
     * @return
     */
    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getAllMatches(@RequestParam(required = false) String team) {
        try {
            List<Match> matches = new ArrayList<Match>();

            if (team != null)
            {
                matchRepository.findByHomeTeamContaining(team).forEach(matches::add);
                matchRepository.findByVisitTeamContaining(team).forEach(matches::add);
            }
            else
                matchRepository.findAll().forEach(matches::add);

            if (matches.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(matches, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Wyszukuje mecz o danym id
     * @param id
     * @return
     */
    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable("id") long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found GameRole with id = " + id));
        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    /**
     * Zapisuje w bazie nowy mecz
     * @param match
     * @return
     */
    @PostMapping("/matches")
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        try {
            Match _match = matchRepository
                    .save(new Match(match.getDate(), match.getResult(), match.getHomeTeam(), match.getVisitTeam(),
                            match.getVisitPointGuard(), match.getVisitShootingGuard(), match.getVisitSmallForward(),
                            match.getVisitPowerForward(), match.getVisitCenter(),
                            match.getHomePointGuard(), match.getHomeShootingGuard(), match.getHomeSmallForward(),
                            match.getHomePowerForward(), match.getHomeCenter()
                            ));
            return new ResponseEntity<>(_match, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Aktualizuje dane meczu
     * @param id
     * @param match
     * @return
     */
    @PutMapping("/matches/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable("id") long id, @RequestBody Match match) {
        Optional<Match> _matchData = matchRepository.findById(id);
        if (_matchData.isPresent()) {
            Match _match = _matchData.get();
            _match.setDate(match.getDate());
            _match.setResult(match.getResult());
            _match.setHomeTeam(match.getHomeTeam());
            _match.setVisitTeam(match.getVisitTeam());
            _match.setVisitPointGuard(match.getVisitPointGuard());
            _match.setVisitShootingGuard(match.getVisitShootingGuard());
            _match.setVisitSmallForward(match.getVisitSmallForward());
            _match.setVisitPowerForward(match.getVisitPowerForward());
            _match.setVisitCenter(match.getVisitCenter());
            _match.setHomePointGuard(match.getHomePointGuard());
            _match.setHomeShootingGuard(match.getHomeShootingGuard());
            _match.setHomeSmallForward(match.getHomeSmallForward());
            _match.setHomePowerForward(match.getHomePowerForward());
            _match.setHomeCenter(match.getHomeCenter());
            return new ResponseEntity<>(matchRepository.save(_match), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Usuwa dane meczu
     * @param id
     * @return
     */
    @DeleteMapping("/matches/{id}")
    public ResponseEntity<HttpStatus> deleteMatch(@PathVariable("id") long id) {
        try {
            matchRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Usuwa wszystkie mecze
     * @return
     */
    @DeleteMapping("/matches")
    public ResponseEntity<HttpStatus> deleteAllMatches() {
        try {
            matchRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
