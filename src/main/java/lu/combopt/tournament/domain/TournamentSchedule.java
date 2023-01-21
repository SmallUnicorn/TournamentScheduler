package lu.combopt.tournament.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.constraint.Indictment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;

@PlanningSolution
public class TournamentSchedule {
    private Long id;
    private List<Stadium> stadiumList;
    private List<Team> teamList;
    @ValueRangeProvider(id="datetimeslots")
    @ProblemFactCollectionProperty
    private List<DateTimeSlot> dateTimeSlotList;
    @PlanningEntityCollectionProperty
    private List<Game> gameList;
    @PlanningScore
    private HardSoftScore score;

    public TournamentSchedule() {
        this.setGameList(new LinkedList<>());
        this.setStadiumList(new LinkedList<>());
        this.setDateTimeSlotList(new LinkedList<>());
        this.setTeamList(new LinkedList<>());
    }

    @JsonIgnore
    public List<List<LocalDate>> getCalendar() {

        List<List<LocalDate>> dates = new LinkedList<>();

        LocalDate minDate = this.getGameList().stream()
                .filter(game -> game.getDateTimeSlot()!= null)
                .map(Game::getGameDate)
                .min(LocalDate::compareTo).get();
        LocalDate maxDate = this.getGameList().stream()
                .filter(game -> game.getDateTimeSlot() != null)
                .map(Game::getGameDate)
                .max(LocalDate::compareTo).get();

        while(minDate.getDayOfWeek() != DayOfWeek.MONDAY)
            minDate = minDate.minusDays(1l);
        while(maxDate.getDayOfWeek() != DayOfWeek.SUNDAY)
            maxDate = maxDate.plusDays(1l);

        int day = 0;

        LocalDate current = minDate;
        while(!current.isAfter(maxDate)){
            if(day % 7 == 0){
                dates.add(new LinkedList<>());
            }
            dates.get(dates.size()-1).add(current);
            current = current.plusDays(1l);
            day++;
        }
        return dates;
    }

    @JsonIgnore
    public String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM"));

    }
    @JsonIgnore
    public List<Game> getGamesForDay(LocalDate date) {
        return this.getGameList().stream()
                .filter(game -> game.getDateTimeSlot()!= null &&
                        game.getGameDate().equals(date))
                .sorted(Comparator.comparing(Game::getGameTime))
                .collect(Collectors.toList());

    }

    @JsonIgnore
    public String getScoreIndictmentsText(Object object, Indictment<HardSoftScore> indictment) {
        if (indictment == null || (indictment.getScore().getHardScore() == 0 && indictment.getScore().getSoftScore() == 0)) {
            return "no impact";
        }
        return "<b>Total score: " + indictment.getScore() + "</b><br />"
                + indictment.getConstraintMatchSet().stream()
                .map(constraintMatch -> constraintMatch.getConstraintName() + " = " + constraintMatch.getScore())
                .collect(Collectors.joining("<br />"));
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
