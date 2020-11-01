import java.util.Objects;

public class State {
    String name;            // The name of the state
    int electoralVotes;     // How many electors it has
    int popularVotes;       // The number of people in that state who voted

    public State(String name, int electoralVotes, int popularVotes) {
        this.name = name;
        this.electoralVotes = electoralVotes;
        this.popularVotes = popularVotes;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", electoralVotes=" + electoralVotes +
                ", popularVotes=" + popularVotes +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return electoralVotes == state.electoralVotes &&
                popularVotes == state.popularVotes &&
                Objects.equals(name, state.name);
    }
}
