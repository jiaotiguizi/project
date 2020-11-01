import java.util.ArrayList;

public class ElectionTest {
    ArrayList<State> allStates;  // All the states that voted
    int minPopularVoteNeeded;    // The popular vote needed
    int year;                    // What year it is

    public ElectionTest(ArrayList<State> allStates, int minPopularVoteNeeded, int year) {
        this.allStates = allStates;
        this.minPopularVoteNeeded = minPopularVoteNeeded;
        this.year = year;
    }

    @Override
    public String toString() {
        return "ElectionTest{" +
                "allStates=" + allStates +
                ", minPopularVoteNeeded=" + minPopularVoteNeeded +
                ", year=" + year +
                '}';
    }
}
