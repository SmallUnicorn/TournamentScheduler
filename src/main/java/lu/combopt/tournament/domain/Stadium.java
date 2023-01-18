package lu.combopt.tournament.domain;

public class Stadium {
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
