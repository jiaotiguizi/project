import java.util.ArrayList;

public class MinInfo {
    int popularVotesNeeded; // How many popular votes you'd need.
    ArrayList<State> statesUsed; // Which states you'd carry in the course of doing so.

    public MinInfo(int popularVotesNeeded, ArrayList<State> statesUsed) {
        this.popularVotesNeeded = popularVotesNeeded;
        this.statesUsed = statesUsed;
    }

    @Override
    public String toString() {
        return "MinInfo{" +
                "popularVotesNeeded=" + popularVotesNeeded +
                ", statesUsed=" + statesUsed +
                '}';
    }
}
