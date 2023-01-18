package lu.combopt.tournament;

import lu.combopt.tournament.domain.*;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        LOGGER.debug("Scheduler start!");
        SolverFactory<TournamentSchedule> solverFactory = SolverFactory.create(SolverConfig.createFromXmlResource("lu/compopt/tournament/solverConfig.xml"));

        //Load the problem
        TournamentSchedule problem = generateData();

        //Solve the problem
        Solver<TournamentSchedule> solver = solverFactory.buildSolver();
        TournamentSchedule solution = solver.solve(problem);

        //Visualize the solution

    }

    public static TournamentSchedule generateData(){
        List<DateTimeSlot> dateTimeSlotList = new LinkedList<>();
        for(int day = 1; day <= 7; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(LocalDateTime.of(2023, Month.JANUARY, day, startTime, 0, 0)));
            }
        }

        Stadium stadCamp = new Stadium("Camp Nou");
        Stadium stadWem = new Stadium("Wembley");
        Stadium stadSan = new Stadium("San Siro");
        List<Stadium> stadiumList = List.of(stadCamp, stadWem, stadSan);

        Team teamBarc = new Team("Barcelona", stadCamp);
        Team teamLiv = new Team("Liverpool", stadWem);
        Team teamInt = new Team("Inter", stadSan);
        Team teamMil = new Team("Milan", stadSan);
        List<Team> teamList = List.of(teamBarc, teamInt, teamMil, teamLiv);

        List<Game> gameList = new LinkedList<>();
        for(int i = 0; i < teamList.size() - 1; i++){
            for (int j = i + 1; j < teamList.size(); j++){
                gameList.add(new Game(teamList.get(i), teamList.get(j), null, teamList.get(i).getStadium()));
                gameList.add(new Game(teamList.get(j), teamList.get(i), null, teamList.get(j).getStadium()));

            }
        }

        TournamentSchedule schedule = new TournamentSchedule();
        schedule.setDateTimeSlotList(dateTimeSlotList);
        schedule.setStadiumList(stadiumList);
        schedule.setTeamList(teamList);
        schedule.setGameList(gameList);

        return schedule;
    }
}
