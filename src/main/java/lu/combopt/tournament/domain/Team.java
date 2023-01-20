package lu.combopt.tournament.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class Team {
    @PlanningId
    private String name;
    private Stadium stadium;

    public Team(){};

    public Team(String name){
        this.setName(name);
    }
    public Team(String name, Stadium stadium){
        this.setName(name);
        this.setStadium(stadium);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

}
