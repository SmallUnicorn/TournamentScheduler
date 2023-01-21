package lu.combopt.tournament.domain;

import com.fasterxml.jackson.annotation.*;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "gameName")
@PlanningEntity
public class Game {
    @PlanningId
    private String gameName;
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
        this.setGameName();
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

    @JsonIgnore
    public LocalDate getGameDate(){
        return getDateTimeSlot().getDateTimeStart().toLocalDate();
    }
    @JsonIgnore
    public LocalTime getGameTime(){
        return getDateTimeSlot().getDateTimeStart().toLocalTime();
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }
    @Override
    public String toString(){
        return this.homeTeam.getName() + " - " + this.awayTeam.getName();
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName() {
        this.gameName = this.toString();
    }
}
