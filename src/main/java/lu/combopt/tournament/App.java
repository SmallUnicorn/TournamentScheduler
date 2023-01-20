package lu.combopt.tournament;

import lu.combopt.tournament.domain.*;
import org.optaplanner.benchmark.api.PlannerBenchmark;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        LOGGER.debug("Scheduler start!");
        //SolverFactory<TournamentSchedule> solverFactory = SolverFactory.create(SolverConfig.createFromXmlResource("lu/combopt/tournament/solverConfig.xml"));

        generateProblems();
        //Load the problem


        //Solve the problem
        //Solver<TournamentSchedule> solver = solverFactory.buildSolver();
        //TournamentSchedule solution = solver.solve(problem);

        PlannerBenchmarkFactory benchmarkFactory = PlannerBenchmarkFactory.createFromXmlResource(
                "lu/combopt/tournament/benchmarkConfig.xml");

        PlannerBenchmark benchmark = benchmarkFactory.buildPlannerBenchmark();
        benchmark.benchmarkAndShowReportInBrowser();

        //ScoreManager<TournamentSchedule, HardSoftScore> scoreScoreManager = ScoreManager.create(solverFactory);
        //LOGGER.debug(scoreScoreManager.explainScore(solution).getSummary());
        //Visualize the solution

    }

    public static void generateProblems(){
        TournamentSchedule problem = generateData();
        TournamentSchedule problem1 = generateData1();

        TournamentScheduleJsonSolutionFileIO objIO = new TournamentScheduleJsonSolutionFileIO();
        objIO.write(problem, new File("data/problem.json"));
        objIO.write(problem1, new File("data/problem1.json"));
    }

    public static TournamentSchedule generateData(){
        List<DateTimeSlot> dateTimeSlotList = new LinkedList<>();
        Integer id = 1;
        for(int day = 1; day <= 14; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(id++, LocalDateTime.of(2023, Month.JANUARY, day, startTime, 0, 0)));
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
                gameList.add(new Game(teamList.get(i), teamList.get(j), dateTimeSlotList.get(0), teamList.get(i).getStadium()));
                gameList.add(new Game(teamList.get(j), teamList.get(i), dateTimeSlotList.get(0), teamList.get(j).getStadium()));

            }
        }

        TournamentSchedule schedule = new TournamentSchedule();
        schedule.setDateTimeSlotList(dateTimeSlotList);
        schedule.setStadiumList(stadiumList);
        schedule.setTeamList(teamList);
        schedule.setGameList(gameList);

        return schedule;
    }

    public static TournamentSchedule generateData1(){
        List<DateTimeSlot> dateTimeSlotList = new LinkedList<>();
        Integer id = 1;
        for(int day = 1; day <= 28; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(id++, LocalDateTime.of(2023, Month.FEBRUARY, day, startTime, 0, 0)));
            }
        }

        Stadium stadCamp = new Stadium("Camp Nou");
        Stadium stadWem = new Stadium("Wembley");
        Stadium stadPark = new Stadium("Park de Prance");
        Stadium stadStem = new Stadium("Stanford bridge");
        Stadium stadSan = new Stadium("San Siro");
        List<Stadium> stadiumList = List.of(stadCamp, stadWem, stadSan);

        Team teamBarc = new Team("Barcelona", stadCamp);
        Team teamLiv = new Team("Liverpool", stadWem);
        Team teamInt = new Team("Inter", stadSan);
        Team teamMil = new Team("Milan", stadSan);
        Team teamPsg = new Team("PSG", stadPark);
        Team teamMan = new Team("Manchester United", stadStem);
        List<Team> teamList = List.of(teamBarc, teamInt, teamMil, teamLiv, teamPsg, teamMan);

        List<Game> gameList = new LinkedList<>();
        for(int i = 0; i < teamList.size() - 1; i++){
            for (int j = i + 1; j < teamList.size(); j++){
                gameList.add(new Game(teamList.get(i), teamList.get(j), dateTimeSlotList.get(0), teamList.get(i).getStadium()));
                gameList.add(new Game(teamList.get(j), teamList.get(i), dateTimeSlotList.get(0), teamList.get(j).getStadium()));

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
