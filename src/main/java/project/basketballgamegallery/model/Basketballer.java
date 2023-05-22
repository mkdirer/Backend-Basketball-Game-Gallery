package project.basketballgamegallery.model;

import javax.persistence.*;

@Entity
@Table(name = "basketballer")
public class Basketballer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "age")
    private int age;
    @Column(name = "team")
    private String team;

    public Basketballer() {
    }
    public Basketballer(String firstname, String lastname, int age, String team) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.team = team;
    }
    public Long getId() {
        return id;
    }
    public String getFirstName() { return firstname; }
    public void setFirstName(String firstname) { this.firstname = firstname; }
    public String getLastName() { return lastname; }
    public void setLastName(String lastname) { this.lastname = lastname; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
    @Override
    public String toString() {
        return "Very well player";
    }
}
