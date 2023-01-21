package lu.combopt.tournament;

import lu.combopt.tournament.domain.*;
import org.optaplanner.benchmark.api.PlannerBenchmark;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
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

        //generateProblems();
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
        TournamentSchedule problem2 = generateData2();
        TournamentSchedule problem3 = generateData3();

        TournamentScheduleJsonSolutionFileIO objIO = new TournamentScheduleJsonSolutionFileIO();
        objIO.write(problem, new File("data/problem.json"));
        objIO.write(problem1, new File("data/problem1.json"));
        objIO.write(problem2, new File("data/problem2.json"));
        objIO.write(problem3, new File("data/problem3.json"));
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
        schedule.setId(1l);
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
        List<Stadium> stadiumList = List.of(stadCamp, stadWem, stadSan, stadPark, stadStem);

        Team teamBarc = new Team("Barcelona", stadCamp);
        Team teamLiv = new Team("Liverpool", stadWem);
        Team teamInt = new Team("Inter", stadSan);
        Team teamMil = new Team("Milan", stadSan);
        Team teamPsg = new Team("PSG", stadPark);
        Team teamChel = new Team("Chelsea", stadStem);
        List<Team> teamList = List.of(teamBarc, teamInt, teamMil, teamLiv, teamPsg, teamChel);

        List<Game> gameList = new LinkedList<>();
        for(int i = 0; i < teamList.size() - 1; i++){
            for (int j = i + 1; j < teamList.size(); j++){
                gameList.add(new Game(teamList.get(i), teamList.get(j), dateTimeSlotList.get(0), teamList.get(i).getStadium()));
                gameList.add(new Game(teamList.get(j), teamList.get(i), dateTimeSlotList.get(0), teamList.get(j).getStadium()));

            }
        }

        TournamentSchedule schedule = new TournamentSchedule();
        schedule.setId(2l);
        schedule.setDateTimeSlotList(dateTimeSlotList);
        schedule.setStadiumList(stadiumList);
        schedule.setTeamList(teamList);
        schedule.setGameList(gameList);

        return schedule;
    }

    public static TournamentSchedule generateData2(){
        List<DateTimeSlot> dateTimeSlotList = new LinkedList<>();
        Integer id = 1;
        for(int day = 1; day <= 31; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(id++, LocalDateTime.of(2023, Month.MAY, day, startTime, 0, 0)));
            }
        }
        for(int day = 1; day <= 30; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(id++, LocalDateTime.of(2023, Month.JUNE, day, startTime, 0, 0)));
            }
        }

        Stadium stadCamp = new Stadium("Camp Nou");
        Stadium stadWem = new Stadium("Wembley");
        Stadium stadPark = new Stadium("Park de Prance");
        Stadium stadOld = new Stadium("Old Trafford");
        Stadium stadSan = new Stadium("San Siro");
        Stadium stadStam = new Stadium("Stamford Bridge");
        Stadium stadSant = new Stadium("Santiago Bernabeu");

        List<Stadium> stadiumList = List.of(stadStam, stadCamp , stadWem, stadSan, stadPark, stadOld, stadSant);

        Team teamBarc = new Team("Barcelona", stadCamp);
        Team teamLiv = new Team("Liverpool", stadWem);
        Team teamInt = new Team("Inter", stadSan);
        Team teamMil = new Team("Milan", stadSan);
        Team teamPsg = new Team("PSG", stadPark);
        Team teamChel = new Team("Chelsea", stadStam);
        Team teamReal = new Team("Real Madrid", stadSant);
        Team teamMan = new Team("Manchester United", stadOld);
        List<Team> teamList = List.of(teamBarc, teamInt, teamMil, teamLiv, teamPsg, teamMan, teamReal, teamChel);

        List<Game> gameList = new LinkedList<>();
        for(int i = 0; i < teamList.size() - 1; i++){
            for (int j = i + 1; j < teamList.size(); j++){
                gameList.add(new Game(teamList.get(i), teamList.get(j), dateTimeSlotList.get(0), teamList.get(i).getStadium()));
                gameList.add(new Game(teamList.get(j), teamList.get(i), dateTimeSlotList.get(0), teamList.get(j).getStadium()));

            }
        }

        TournamentSchedule schedule = new TournamentSchedule();
        schedule.setId(3l);
        schedule.setDateTimeSlotList(dateTimeSlotList);
        schedule.setStadiumList(stadiumList);
        schedule.setTeamList(teamList);
        schedule.setGameList(gameList);

        return schedule;
    }

    public static TournamentSchedule generateData3(){
        List<DateTimeSlot> dateTimeSlotList = new LinkedList<>();
        Integer id = 1;
        for(int day = 1; day <= 31; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(id++, LocalDateTime.of(2023, Month.MAY, day, startTime, 0, 0)));
            }
        }
        for(int day = 1; day <= 30; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(id++, LocalDateTime.of(2023, Month.JUNE, day, startTime, 0, 0)));
            }
        }
        for(int day = 1; day <= 31; day++){
            for(int startTime = 8; startTime <= 22; startTime += 2){
                dateTimeSlotList.add(new DateTimeSlot(id++, LocalDateTime.of(2023, Month.JULY, day, startTime, 0, 0)));
            }
        }

        Stadium stadCamp = new Stadium("Camp Nou");
        Stadium stadWem = new Stadium("Wembley");
        Stadium stadPark = new Stadium("Park de Prance");
        Stadium stadOld = new Stadium("Old Trafford");
        Stadium stadSan = new Stadium("San Siro");
        Stadium stadStam = new Stadium("Stamford Bridge");
        Stadium stadSant = new Stadium("Santiago Bernabeu");

        List<Stadium> stadiumList = List.of(stadStam, stadCamp , stadWem, stadSan, stadPark, stadOld, stadSant);

        Team teamBarc = new Team("Barcelona", stadCamp);
        Team teamLiv = new Team("Liverpool", stadWem);
        Team teamInt = new Team("Inter", stadSan);
        Team teamMil = new Team("Milan", stadSan);
        Team teamPsg = new Team("PSG", stadPark);
        Team teamChel = new Team("Chelsea", stadStam);
        Team teamReal = new Team("Real Madrid", stadSant);
        Team teamMan = new Team("Manchester United", stadOld);
        List<Team> teamList = List.of(teamBarc, teamInt, teamMil, teamLiv, teamPsg, teamMan, teamReal, teamChel);

        List<Game> gameList = new LinkedList<>();
        for(int i = 0; i < teamList.size() - 1; i++){
            for (int j = i + 1; j < teamList.size(); j++){
                gameList.add(new Game(teamList.get(i), teamList.get(j), dateTimeSlotList.get(0), teamList.get(i).getStadium()));
                gameList.add(new Game(teamList.get(j), teamList.get(i), dateTimeSlotList.get(0), teamList.get(j).getStadium()));

            }
        }

        TournamentSchedule schedule = new TournamentSchedule();
        schedule.setId(4l);
        schedule.setDateTimeSlotList(dateTimeSlotList);
        schedule.setStadiumList(stadiumList);
        schedule.setTeamList(teamList);
        schedule.setGameList(gameList);

        return schedule;
    }
}
