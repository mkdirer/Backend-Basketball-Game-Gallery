package project.basketballgamegallery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gamerole")
public class GameRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "points")
    private int points;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Team team;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "gamerole_player",
            joinColumns = { @JoinColumn(name = "gameroles_id") },
            inverseJoinColumns = { @JoinColumn(name = "player_id") })
    private Set<Player> players = new HashSet<>();

    public GameRole()
    {
    }

    public GameRole(int points)
    {
        this.points = points;
    }

    public long getId() {
        return id;
    }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public void addPlayer(Player player) {
        this.players.add(player);
        player.getGameRoles().add(this);
    }

    public void removePlayer(long playerId) {
        Player player = this.players.stream().filter(p -> p.getId() == playerId).findFirst().orElse(null);
        if (player != null) {
            this.players.remove(player);
            player.getGameRoles().remove(this);
        }
    }

    public Match getMatch() { return this.match; }
    public void setMatch(Match match) { this.match = match; }

    @Override
    public String toString() {
        return "sklad gracza";
    }
}
