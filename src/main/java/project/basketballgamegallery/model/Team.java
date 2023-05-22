package project.basketballgamegallery.model;

import javax.persistence.*;
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "league")
    private String league;
    public Team() {
    }
    public Team(String name, String country, String league) {
        this.name = name;
        this.country = country;
        this.league = league;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getLeague() {
        return league;
    }
    public void setLeague(String league) {
        this.league = league;
    }
    @Override
    public String toString() {
        return "My Teams";
    }
}
