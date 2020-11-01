import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WinningThePresidency {

    // Constant representing that there is no way to win the election with the given number of votes.
    public static final int kNotPossible = 1000000000;

    public static void main(String[] args){
        try{
            ElectionTest test = readDataFile("data/1828.csv", true);
            System.out.println(test);

            MinInfo info = minPopularVoteToWin(test.allStates);
            System.out.println(info);

            int diff = info.popularVotesNeeded - test.minPopularVoteNeeded;
            System.out.printf("%10s %8d %10s %8d\n", "Optimal:", test.minPopularVoteNeeded, "Yours:", info.popularVotesNeeded);
            System.out.println("Difference from optimal solution: " + diff);
            if(diff == 0) {
                System.out.println("Nice Work!");
            }
            else if(diff  < 0)
                System.out.println("Beat the optimal? That's probably not right...");
            else
                System.out.println("Bigger than the optimal. Keep at it - you'll get there!");
        }
        catch(Exception ex){
            System.out.println("File Read Error");
            ex.printStackTrace();
        }
    }

    public static MinInfo minPopularVoteToWin(ArrayList<State> states){
        ArrayList<State> includedStates = new ArrayList<>();

        MinInfo info = new MinInfo(kNotPossible, includedStates); // default value for no states included and no solution

        //implement method here.

        return info;
    }


    public static ElectionTest readDataFile(String filename, boolean simplified) throws IOException{
        int simplifiedLinesToRead = 10; //if the simplified flag is true, then only read 10 lines.
        int minPopularVotesNeeded = -1, year = -1;
        ArrayList<State> states = new ArrayList<>();
        Scanner fileReader = new Scanner(new File(filename));
        String[] firstLine = fileReader.nextLine().split(",");
        int votes = simplified ? 2 : 1;

        year = Integer.parseInt(firstLine[0]);
        minPopularVotesNeeded = Integer.parseInt(firstLine[votes]);

        int linesRead = 0;
        while(fileReader.hasNextLine() && (!simplified || linesRead < simplifiedLinesToRead)){
            String[] fields = fileReader.nextLine().split(",");
            State state = new State(fields[0], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
            states.add(state);
            linesRead++;
        }

        ElectionTest test = new ElectionTest(states, minPopularVotesNeeded, year);
        return test;
    }
}
