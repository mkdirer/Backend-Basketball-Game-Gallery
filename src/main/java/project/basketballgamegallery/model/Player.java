package project.basketballgamegallery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_generator")
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "age")
    private int age;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Team team;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "players")
    @JsonIgnore
    private Set<GameRole> gameRoles = new HashSet<>();

    public Player() {
    }
    public Player(String firstname, String lastname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }
    public long getId() {
        return id;
    }
    public String getFirstName() { return firstname; }
    public void setFirstName(String firstname) { this.firstname = firstname; }
    public String getLastName() { return lastname; }
    public void setLastName(String lastname) { this.lastname = lastname; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
    public Set<GameRole> getGameRoles() {
        return gameRoles;
    }
    public void setGameRoles(Set<GameRole> gameRoles) {
        this.gameRoles = gameRoles;
    }
    @Override
    public String toString() {
        return "Player gracz";
    }
}
