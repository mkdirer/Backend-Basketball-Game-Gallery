package project.basketballgamegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.basketballgamegallery.model.Team;
import project.basketballgamegallery.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Kontroler rutingu danych o drużynach koszykarskich
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TeamController {
    @Autowired
    TeamRepository teamRepository;

    /**
     * Zwraca wszystkie drużyny lub te zawierajace okreslona nazwe
     * @param name
     * @return
     */
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams(@RequestParam(required = false) String name) {
        try {
            List<Team> teams = new ArrayList<Team>();
            if (name == null)
                teamRepository.findAll().forEach(teams::add);
            else
                teamRepository.findByNameContaining(name).forEach(teams::add);
            if (teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Zwraca drużyne o danym id
     * @param id
     * @return
     */
    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") long id) {
        Optional<Team> clubData = teamRepository.findById(id);
        if (clubData.isPresent()) {
            return new ResponseEntity<>(clubData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Tworzy nową drużyne
     * @param team
     * @return
     */
    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        try {
            Team _team = teamRepository
                    .save(new Team(team.getName(), team.getCountry(), team.getLeague()));
            return new ResponseEntity<>(_team, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Aktualizuje dane drużyny
     * @param id
     * @param team
     * @return
     */
    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") long id, @RequestBody Team team) {
        Optional<Team> clubData = teamRepository.findById(id);
        if (clubData.isPresent()) {
            Team _team = clubData.get();
            _team.setName(team.getName());
            _team.setCountry(team.getCountry());
            _team.setLeague(team.getLeague());
            return new ResponseEntity<>(teamRepository.save(_team), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Usuwa drużyne o zadanym id
     * @param id
     * @return
     */
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable("id") long id) {
        try {
            teamRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Usuwa wszystkie drużyny
     * @return
     */
    @DeleteMapping("/teams")
    public ResponseEntity<HttpStatus> deleteAllTeams() {
        try {
            teamRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/teams/league")
    public ResponseEntity<List<Team>> findByPublished(@RequestParam(required = true) String league) {
        try {
            List<Team> teams = teamRepository.findByLeagueContaining(league);
            if (teams.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
