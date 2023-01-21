package lu.combopt.tournament.rest;

import lu.combopt.tournament.domain.TournamentSchedule;
import lu.combopt.tournament.domain.TournamentScheduleJsonSolutionFileIO;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/optimizer")
public class TournamentController {
    private TournamentScheduleJsonSolutionFileIO fileIO = new TournamentScheduleJsonSolutionFileIO();
    @Autowired
    private SolverManager<TournamentSchedule, Long> solverManager;
    @Autowired
    private ScoreManager<TournamentSchedule, HardSoftScore> scoreManager;

    private Map<Long, TournamentSchedule> scheduleList = new HashMap<Long, TournamentSchedule>();

    @GetMapping("/home")
    @ResponseBody
    public String home(){
        TournamentSchedule problem = fileIO.read(new File("data/problem.json"));
        solverManager.solve(1l, problem);
        return "Hi!";
    }

    @PostMapping("/solve")
    @ResponseBody
    public TournamentSchedule solve(@RequestBody TournamentSchedule problem) throws ExecutionException, InterruptedException {
        return solverManager.solve(1l, problem).getFinalBestSolution();
    }
    @PostMapping("/solve2")
    @ResponseBody
    public void solve2(@RequestBody TournamentSchedule problem){
        solverManager.solveAndListen(problem.getId(), id -> problem, solution -> scheduleList.put(solution.getId(), solution));
    }

    @GetMapping("/get")
    @ResponseBody
    public TournamentSchedule getSolution(@RequestParam(name="id") Long id){
        return scheduleList.get(id);
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("list",
                scheduleList.values().stream()
                        .collect(Collectors.toList()));
        return "list";
    }
    @GetMapping("/show")
    public String show(@RequestParam(name="scheduleId") Long scheduleId, Model model){
        List<String> weekdays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        TournamentSchedule schedule = this.scheduleList.getOrDefault(scheduleId, null);
        model.addAttribute("indictmentMap", this.scoreManager.explainScore(schedule).getIndictmentMap());
        model.addAttribute("calendar", schedule.getCalendar());
        model.addAttribute("weekdays", weekdays);
        model.addAttribute("schedule", schedule);
        return "show";
    }


}
