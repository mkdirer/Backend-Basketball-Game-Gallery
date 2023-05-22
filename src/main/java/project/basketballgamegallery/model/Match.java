package project.basketballgamegallery.model;

import javax.persistence.*;

@Entity
@Table(name = "match2")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private String date;
    @Column(name = "result")
    private String result;
    @Column(name = "homeTeam")
    private String homeTeam;
    @Column(name = "visitTeam")
    private String visitTeam;

    @Column(name = "visitPointGuard")
    private String visitPointGuard;
    @Column(name = "visitShootingGuard")
    private String visitShootingGuard;
    @Column(name = "visitSmallForward")
    private String visitSmallForward;
    @Column(name = "visitPowerForward")
    private String visitPowerForward;
    @Column(name = "visitCenter")
    private String visitCenter;

    @Column(name = "homePointGuard")
    private String homePointGuard;
    @Column(name = "homeShootingGuard")
    private String homeShootingGuard;
    @Column(name = "homeLeftCenterBack")
    private String homeSmallForward;
    @Column(name = "homePowerForward")
    private String homePowerForward;
    @Column(name = "homeCenter")
    private String homeCenter;

    public Match()
    {
    }

    public Match(String date, String result, String homeTeam, String visitTeam,
                 String visitPointGuard, String visitShootingGuard, String visitSmallForward, String visitPowerForward,
                 String visitCenter,
                 String homePointGuard, String homeShootingGuard, String homeSmallForward, String homePowerForward,
                 String homeCenter)
    {
        this.date = date;
        this.result = result;
        this.homeTeam = homeTeam;
        this.visitTeam = visitTeam;
        this.setVisitPointGuard(visitPointGuard);
        this.setVisitShootingGuard(visitShootingGuard);
        this.setVisitSmallForward(visitSmallForward);
        this.setVisitPowerForward(visitPowerForward);
        this.setVisitCenter(visitCenter);
        this.setHomePointGuard(homePointGuard);
        this.setHomeShootingGuard(homeShootingGuard);
        this.setHomeSmallForward(homeSmallForward);
        this.setHomePowerForward(homePowerForward);
        this.setHomeCenter(homeCenter);
    }

    public long getId() {
        return id;
    }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }
    public String getVisitTeam() { return visitTeam; }
    public void setVisitTeam(String visitTeam) { this.visitTeam = visitTeam; }


    public String getVisitPointGuard() {
        return visitPointGuard;
    }

    public void setVisitPointGuard(String visitPointGuard) {
        this.visitPointGuard = visitPointGuard;
    }

    public String getVisitShootingGuard() {
        return visitShootingGuard;
    }

    public void setVisitShootingGuard(String visitShootingGuard) {
        this.visitShootingGuard = visitShootingGuard;
    }

    public String getVisitSmallForward() {
        return visitSmallForward;
    }

    public void setVisitSmallForward(String visitSmallForward) {
        this.visitSmallForward = visitSmallForward;
    }

    public String getVisitPowerForward() {
        return visitPowerForward;
    }

    public void setVisitPowerForward(String visitPowerForward) {
        this.visitPowerForward = visitPowerForward;
    }

    public String getVisitCenter() {
        return visitCenter;
    }

    public void setVisitCenter(String visitCenter) {
        this.visitCenter = visitCenter;
    }

    public String getHomePointGuard() {
        return homePointGuard;
    }

    public void setHomePointGuard(String homePointGuard) {
        this.homePointGuard = homePointGuard;
    }

    public String getHomeShootingGuard() {
        return homeShootingGuard;
    }

    public void setHomeShootingGuard(String homeShootingGuard) {
        this.homeShootingGuard = homeShootingGuard;
    }

    public String getHomeSmallForward() {
        return homeSmallForward;
    }

    public void setHomeSmallForward(String homeSmallForward) {
        this.homeSmallForward = homeSmallForward;
    }

    public String getHomePowerForward() {
        return homePowerForward;
    }

    public void setHomePowerForward(String homePowerForward) {
        this.homePowerForward = homePowerForward;
    }

    public String getHomeCenter() {
        return homeCenter;
    }

    public void setHomeCenter(String homeCenter) {
        this.homeCenter = homeCenter;
    }

}
