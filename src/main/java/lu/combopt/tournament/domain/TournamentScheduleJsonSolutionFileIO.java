package lu.combopt.tournament.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.optaplanner.persistence.jackson.impl.domain.solution.JacksonSolutionFileIO;
public class TournamentScheduleJsonSolutionFileIO extends JacksonSolutionFileIO<TournamentSchedule> {
    public TournamentScheduleJsonSolutionFileIO(){
        super(TournamentSchedule.class, new ObjectMapper().findAndRegisterModules());
    }
}
