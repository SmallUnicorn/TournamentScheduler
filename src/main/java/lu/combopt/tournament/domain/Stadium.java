package lu.combopt.tournament.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class Stadium {
    @PlanningId
    private String name;

    public Stadium(){};
    public Stadium(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
