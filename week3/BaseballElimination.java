import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import java.util.HashMap;

public class BaseballElimination
{
    private final int numberOfTeams;
    private HashMap<String, Integer> teams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;

    // create a baseball division from given filename in format specified belo
    public BaseballElimination(String filename)
    {
        In in = new In(filename);
        numberOfTeams = in.readInt();
        teams = new HashMap<String, Integer>();
        w = new int[numberOfTeams];
        l = new int[numberOfTeams];
        r = new int[numberOfTeams];
        g = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teams.put(in.readString(), i);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; j++) {
                g[i][j] = in.readInt();
            }
        }
    }

    // number of teams
    public int numberOfTeams()
    {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams()
    {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team)
    {
        return w[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team)
    {
        return l[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team)
    {
        return r[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        return g[teams.get(team1)][teams.get(team2)];
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
        // Philadelphia is eliminated by the subset R = { Atlanta New_York } 
        // New_York is not eliminated
        // Montreal is eliminated by the subset R = { Atlanta }
        // % java BaseballElimination teams5.txt 
        // New_York is not eliminated
        // Baltimore is not eliminated
        // Boston is not eliminated
        // Toronto is not eliminated
        // Detroit is eliminated by the subset R = { New_York Baltimore Boston Toronto }
    }
}
