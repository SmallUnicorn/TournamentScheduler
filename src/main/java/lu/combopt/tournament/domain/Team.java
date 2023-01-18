package lu.combopt.tournament.domain;

public class Team {
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
