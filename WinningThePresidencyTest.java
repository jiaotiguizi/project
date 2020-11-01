import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WinningThePresidencyTest {

    @org.junit.jupiter.api.Test
    void minPopularVoteToWinOneState() {
        ArrayList<State> states = new ArrayList<>();
        states.add(new State("Denial", 1, 1));
        MinInfo solution = WinningThePresidency.minPopularVoteToWin(states);
        assertEquals(1, solution.popularVotesNeeded);
        assertEquals(states, solution.statesUsed);
    }

    @org.junit.jupiter.api.Test
    void minPopularVoteToWinStatesEmpty() {
        ArrayList<State> states = new ArrayList<>();
        MinInfo solution = WinningThePresidency.minPopularVoteToWin(states);
        assertEquals(WinningThePresidency.kNotPossible, solution.popularVotesNeeded);
        assertEquals(states, solution.statesUsed);
    }

    @org.junit.jupiter.api.Test
    void minPopularVoteToWinStatesTwoStates() {
        ArrayList<State> states = new ArrayList<>();
        states.add(new State("A", 4, 500 ));
        states.add(new State("B", 3, 400 ));

        MinInfo solution = WinningThePresidency.minPopularVoteToWin(states);
        assertEquals(251,solution.popularVotesNeeded);
        assertEquals(1, solution.statesUsed.size());
        assert(solution.statesUsed.contains(states.get(0)));
    }

    @org.junit.jupiter.api.Test
    void minPopularVoteToWinStatesThreeStates() {
        ArrayList<State> states = new ArrayList<>();
        states.add(new State( "A", 4, 500  ));
        states.add(new State("B", 3, 499  ));
        states.add(new State("C", 2, 100 ));

        MinInfo solution = WinningThePresidency.minPopularVoteToWin(states);
        assertEquals(301,solution.popularVotesNeeded);
        assertEquals( 2, solution.statesUsed.size());
        assert(solution.statesUsed.contains(states.get(1)));
        assert(solution.statesUsed.contains(states.get(2)));
    }
}