# Project 4 - Recursive Democracy

The President of the United States is not elected by a popular vote, but by a majority vote in the Electoral College. Each state, plus DC, gets some number of electors in the Electoral College, and whoever they vote in becomes the next President. For the purposes of this problem, we're going to make some simplifying assumptions:

- You need to win a majority of the votes in a state to earn its electors, and you get all the state's electors if you win the majority of the votes. For example, in a small state with 999,999 people, you'd need 500,000 votes to win all its electors. These assumptions aren’t entirely accurate, both because in most states a plurality suffices and some states split their electoral votes in other ways.
- You need to win a majority of the electoral votes to become president. In the 2008 election, you'd need 270 votes because there were 538 electors. In the 1804 election, you'd need 89 votes because there were only 176 electors. (You can technically win the presidency without winning the Electoral College; we'll ignore this for simplicity.)
- Electors never defect. The electors in the Electoral College are free to vote for whomever they please, but the expectation is that they'll vote for the candidate that won their home state. As a simplifying assumption, we'll just pretend electors always vote with the majority of their state.

This problem explores the following question: under these assumptions, what's the fewest number of popular votes you can get and still be elected President? Your task is to write a function`MinInfo minPopularVoteToWin(ArrayList<State> states);` that takes as input a list of all the states that participated in the election (plus DC, if applicable), then returns some information about the minimum number of popular votes you’d need in order to win the election (namely, how many votes you’d need, and which states you’d carry in the process). Here’s a quick overview of the classes involved here. First, there’s the State class, defined here:

```java
public class State {
    String name;        // The name of the state
    int electoralVotes; // How many electors it has
    int popularVotes;   // The number of people in that state who voted
}
```       

The input to minPopularVoteToWin is an ArrayList<State> containing information about all the states that participated in the election. The minPopularVoteToWin function then returns a MinInfo, a class that contains information about the minimum popular vote needed and which states you’d carry:

```java
public class MinInfo {
    int popularVotesNeeded;   // How many popular votes you'd need.
    ArrayList<State> statesUsed; // The states you'd win in getting those votes.
}
```
		
This particular recursive function requires a little creativity to get working quickly, so rather than having you dive headfirst into it, we’ve broken this task down into three smaller pieces that build into the finished product. Our suggested solution route for this problem looks like this:

- *Step One:* Implement a recursive helper function that solves a slightly more general version of this problem.
- *Step Two:* Implement minPopularVoteToWin as a wrapper around that function, getting a preliminary implementation that’s good for small inputs but too slow for full elections.
- *Step Three:* Add in memoization to make your program run lightning fast. Then, go play around with the real election data bundled with the starter files to see everything in action!
The rest of this section describes these steps in detail.

### Teams


- [x] I worked on my own.
- [ ] I worked with *INSERT PARTNER NAME* on this project. The parts I worked on were: *EDIT THIS*

### Testing
New for this homework is the introduction of test cases. These test cases provide instant feedback that your system runs appropriately and provides the right output for a given input. These tests are included in the file `WinningThePresidencyTest.java` and they follow a pretty straightforward pattern. A test method is declared as void, then it sets up a simple version of the problem you are asked to solve. Instead of all fifty-one districts, for example, it might have just two, as in the example below. The test below creates conditions for two states, A and B, and their hypothetical electoral votes and popular votes. 

Because this is such a small example, we can predetermine the result of a well-crafted algorithm by hand - to win the majority of the possible electoral votes here (4 out of 7) with the minimum possible votes, you would need to carry State A with a majority of 251 electoral votes in that state. The 'test' part of this test uses the `assertEquals()` to make sure that two things are equal - the first parameter is what you expected to find, the second is the actual results from your program. So you can see that, right after a solution is obtained from your program, we check that the solution correctly guesses that only 251 electoral votes are needed. Then, we establish that the solution only contains 1 state. Finally, we make sure that the solution contains the correct state using `assert()`, which just makes sure something is true.

```java
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
```

The starter code that you are given compiles but does not pass all of the tests. As you build the code, the test will give you feedback on whether your solution is logically correct or not. In this project, you will also experiment with creating test cases of your own. Follow the pattern established by the example test cases. There is no need to use functions other than `assert()` or `assertEquals()`. 

![img/testResults.png](img/testResults.png)

- The code in the main method helps you determine if you are finding the correct solution overall.
- The code in the test cases help you determine why you might not be finding the correct solution. 

> The testing component of this project is far easier to accomplish using an IDE like the suggested IntelliJ. When you first open the project the `WinningThePresidencyTest.java` file will likely be flagged as unable to compile. The context hint that you will see when you open the test file from IntelliJ is to "add JUnit 5.4 to classpath" and you should select that. If you are given the option to "add JUnit 4 to classpath" you might have to select "more actions" to see the option to get version 5.4 - this is the version you want. Then you will be prompted to download the testing software library from Maven. Once that is done, the file should no longer be flagged, and can be run from the project list by right-clicking on it just like any other class with a main method. 

## Step 1 - Solve a More General Problem
Your first task is to implement this recursive helper function:

```java
public static MinInfo minPopularVoteToGetAtLeast(int electoralVotes,          
                                   int startIndex,              
                                   ArrayList<State> states)
```

This function should solve the following problem:

> How few popular votes are needed to get at least `electoralVotes` electoral votes, using only states from index `startIndex` and greater in an ArrayList of all states?

How is this a generalization of the original problem? Well, in the original problem, you need to specifically get a majority of the electoral votes, and here you need to hit some arbitrary target number of votes. In the original problem, you can use any of the states, and in this modified problem, you can only use states from the specified index and forward.

You must not make this a helper function, and you must not add any additional parameters. Later on, you’re going to revisit this function and add in memoization, and that will not work correctly unless this function is implemented recursively and only uses the specified parameters.

You might find yourself in the unfortunate situation where, for some specific values of electoralVotes and startIndex, there’s no possible way to get electoralVotes votes using only the states from index startIndex and forward. For example, if you’re working with real-world data, are short 75 electoral votes, and only have a single state left, there’s nothing that you can do to win the election. In that case, you may want to have this helper function return a MinInfo whose popularVotesNeeded field is set to sentinel value indicating that it’s not possible to secure that many votes. We’ve provided you with a constant kNotPossible that you can use to indicate this; we chose kNotPossible to be something that’s so big that it will never be the correct answer. The advantage of this sentinel is that you’re already planning on finding the strategy that requires the fewest popular votes, so if your sentinel value is greater than any possible legal number of votes, always choosing the option that requires the minimum number of votes will automatically pull you away from the sentinel value.

**Deliverable 1:**  To summarize, here’s what you need to do.
1.	Add at least one custom test case to WinningThePresidencyTest.java to specifically test your minPopularVoteToGetAtLeast function.
2.	Implement the minPopularVoteToGetAtLeast function in WinningThePresidency.java using the recursive strategy outlined above. As a reminder, you must not add any parameters to this function, nor should you make it a helper.

Some notes on this problem:
- In lecture, you’ve seen a number of structures you can explore recursively. Which of those options does this most closely resemble? Based on that, what sort of recursive approach might you want to take here?
- Implement this function in two stages. First, completely ignore the statesUsed field in the MinInfo you return and just fill in the popularVotesNeeded field. Once you’re giving back the correct number of popular votes, update your function to fill in which states you end up using.
- You need strictly more than half the popular votes to win all the electors from a state. For example, if there are 100,000 or 100,001 people in a state, you need 50,001 votes to win its electors.
- This is probably the toughest of the three steps in this problem, so don’t get discouraged if it seems a bit tricky. You’ve taken on a number of tough recursion problems before and you are absolutely capable of handling this challenge. Use the skills you’ve learned along the way: craft strategic test cases to stress-test unusual scenarios, pull up the debugger if need be, add in some println statements to have the code tell you what it’s doing, etc. You can do this!

## Step 2 - Implement a Preliminary Solution
Now that you have minPopularVoteToGetAtLeast implemented, go and implement the top-level function minPopularVoteToWin by calling minPopularVoteToGetAtLeast with the right set of arguments. You shouldn’t need to write much code for this – this is more about thinking through how the function you wrote in the first part works than it is about crazy complex recursion.

**Deliverable 2:** Specifically, do the following:
1.	Add at least one custom test case to WinningThePresidencyTest.java to specifically test your minPopularVoteToWin function.
2.	Implement the minPopularVoteToWin function in WinningThePresidency.java.

Some things to think about:
- The historical election data – and our sample test cases – do not always include all 50 US states plus DC, either because those states didn’t exist yet, or DC didn’t have the vote, or because those states didn’t participate in the election, so you shouldn’t assume that you’ll get them as input.
- You need to get strictly more than half the possible electoral votes to get elected President. If, as in 2016, there are 538 electors, then you’d need to get 270 votes to win. If, as in 1788, there are 69 electors, then you’d need 35 votes to get elected. In other words, don’t assume you specifically need to get to 270; determine the total based on the collection of states provided as input.

Be sure to test your solution before moving on. You should find that for smaller inputs (say, 15 or fewer states) that your code is relatively fast, but that for larger inputs things start to slow down to a crawl. If you try running what you have on real elections data with all fifty states plus DC, chances are that your code won’t even produce an answer. That’s okay for now; you’ll speed things up in the next step. But do make sure the answers that you do produce are correct; it’s not going to matter that you end up with fast code if the information you generate has no relationship with reality. 


## Step 3 - Add Memoization

At this point, you have an implementation of minPopularVotesToWin that technically works correctly, but which is going to be way too slow to work on real elections data. As an example, all fifty states, plus the District of Columbia, participated in the 2016 election. That means that there are 2<sup>51</sup> possible subsets to explore when trying to find the optimal combination. Unfortunately, 2<sup>51</sup> is 2,251,799,813,685,248, which is such a huge number that even checking a billion combinations each second would take about a month to process.

On the other hand, look back at the recursive helper function you implemented in Step One. It takes as input a number of electoral votes to reach and an index into the ArrayList. In the 2016 election, that number of electoral votes is 270, and there are only 51 states (counting DC as a state). That means there are only 271 × 52 =  14,092 different inputs possible for this helper function (the number of electoral votes ranges from 0 to 270, inclusive, and the start index can range from 0 to 51, inclusive). If you were to memoize the results and only compute each value once, it should take the computer under a second to crunch through all of them. That’s a dramatic improvement!

As your last task, update the minPopularVoteToGetAtLeast function to use memoization. There are many ways you can do this, and we’ll leave those decisions up to you. Adding memoization will change the signature of this function, and that’s completely okay at this stage of the assignment. This may cause some of your older tests to no longer compile; you’ll need to make appropriate edits to them.
Once you’ve gotten this working, try running your code on full elections data. Isn’t that amazing?


**Deliverable 3:** To summarize, here’s what you need to do:
1. Edit your minPopularVoteToGetAtLeast function to support memoization. In doing so:
    1. You’ll need to add a new parameter, or several new parameters, to the function.
    2. You’ll need to edit your test cases from before and minPopularVoteToWin to pass in appropriately-constructed arguments to that function.
    3. You’ll need to fill in the memoization table to avoid recomputing answers.
2.	Test your solution thoroughly. 

Right before the 2016 election, NPR reported that 23% of the popular vote would be sufficient to win the election, based on the 2012 voting data. They arrived at this number by looking at states with the highest ratio of electoral votes to voting population. This was a correction to their originally-reported number of 27%, which they got by looking at what it would take to win the states with the highest number of electoral votes. But the optimal strategy turns out to be neither of these and instead uses a blend of small and large states. Once you’ve gotten your program working, try running it on the data from the 2012 election. What percentage of the popular vote does your program say would be necessary to secure the presidency?

> (A note: the historical election data here was compiled from a number of sources. In many early elections state legislatures chose electors rather than voters, so in some cases we estimated the voting population based on the overall US population at the time and the total fraction of votes cast. This may skew some of the earlier election results. However, to the best of our knowledge, data from 1868 and forward is complete and accurate. Please let us know if you find any errors in our data!)

## Step 4 - Discuss your Results

Once the analysis has concluded, let us know what approach you took.  
1. Try to concisely describe your algorithm in your own words. What approach did you take? Would other approaches be simpler and/or faster?
1. How did memoization affect the rate of computation for your algorithm? Specifically, what did it allow you to do that you could not before?
1. What did you think about the outcome? Do you think it would be possible for a president to win with close to the minimum number of popular votes?

**Deliverable 4 :** Your comments uploaded to Canvas.

# Deliverables and Grading
| Weight | Deliverable                                                                                                           |
|--------|-----------------------------------------------------------------------------------------------------------------------|
| 5     | Test case for minPopularVoteToGetAtLeast method|
| 25     | Implement the minPopularVoteToGetAtLeast method using the recursive strategy.|
| 5     | Test case for winPopularVoteToWin method|
| 25     | Implement the minPopularVotesToWin method.|
| 20     | Edit the minPopularVoteToGetAtLeast method to support memoization |
| 10     | Test your solution thoroughly using test cases and included data files |
| 10     | Your code generates a report with the three required sections.|


