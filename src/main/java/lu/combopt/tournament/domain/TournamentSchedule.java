package lu.combopt.tournament.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.LinkedList;
import java.util.List;

@PlanningSolution
public class TournamentSchedule {
    @PlanningEntityCollectionProperty
    private List<Game> gameList;
    @ValueRangeProvider(id="datetimeslots")
    @ProblemFactCollectionProperty
    private List<DateTimeSlot> dateTimeSlotList;
    private List<Stadium> stadiumList;
    private List<Team> teamList;

    @PlanningScore
    private HardSoftScore score;

    public TournamentSchedule() {
        this.setGameList(new LinkedList<>());
        this.setStadiumList(new LinkedList<>());
        this.setDateTimeSlotList(new LinkedList<>());
        this.setTeamList(new LinkedList<>());
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public List<Stadium> getStadiumList() {
        return stadiumList;
    }

    public void setStadiumList(List<Stadium> stadiumList) {
        this.stadiumList = stadiumList;
    }

    public List<DateTimeSlot> getDateTimeSlotList() {
        return dateTimeSlotList;
    }

    public void setDateTimeSlotList(List<DateTimeSlot> dateTimeSlotList) {
        this.dateTimeSlotList = dateTimeSlotList;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}
