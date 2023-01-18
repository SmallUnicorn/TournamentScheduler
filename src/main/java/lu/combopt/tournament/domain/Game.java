package lu.combopt.tournament.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Game {
    private Team homeTeam;
    private Team awayTeam;
    @PlanningVariable(valueRangeProviderRefs = "datetimeslots")
    private DateTimeSlot dateTimeSlot;
    private Stadium stadium;

    public Game(){};

    public Game(Team homeTeam, Team awayTeam, DateTimeSlot dateTimeSlot, Stadium stadium) {
        this.setHomeTeam(homeTeam);
        this.setAwayTeam(awayTeam);
        this.setDateTimeSlot(dateTimeSlot);
        this.setStadium(stadium);
    }


    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public DateTimeSlot getDateTimeSlot() {
        return dateTimeSlot;
    }

    public void setDateTimeSlot(DateTimeSlot dateTimeSlot) {
        this.dateTimeSlot = dateTimeSlot;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }
}
