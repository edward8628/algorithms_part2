import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination
{
    // create a baseball division from given filename in format specified belo
    public BaseballElimination(String filename)
    {
    }

    // number of teams
    public int numberOfTeams()
    {
        return 0;
    }

    // all teams
    public Iterable<String> teams()
    {
        return null;
    }

    // number of wins for given team
    public int wins(String team)
    {
        return 0;
    }

    // number of losses for given team
    public int losses(String team)
    {
        return 0;
    }

    // number of remaining games for given team
    public int remaining(String team)
    {
        return 0;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        return 0;
    }

    // is given team eliminated?
    public boolean isEliminated(String team)
    {
        return false;
    }

    //subsetRofteamsthateliminatesgiventeam;nullifnoteliminated
    public Iterable<String> certificateOfElimination(String team)
    {
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]); 
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { "); 
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " "); 
                }
                StdOut.println("}"); }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }

        // % java BaseballElimination teams4.txt
        // Atlanta is not eliminated
        // Philadelphia is eliminated by the subset R = { Atlanta New_York } New_York is not eliminated
        // Montreal is eliminated by the subset R = { Atlanta }
        // % java BaseballElimination teams5.txt New_York is not eliminated
        // Baltimore is not eliminated
        // Boston is not eliminated
        // Toronto is not eliminated
        // Detroit is eliminated by the subset R = { New_York Baltimore Boston Toronto }
    }
}
