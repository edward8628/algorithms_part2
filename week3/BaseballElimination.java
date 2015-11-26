import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.HashMap;

public class BaseballElimination
{
    private final int numberOfTeams;
    private HashMap<String, Integer> teams;
    private String[] teamName;
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
        teamName = new String[numberOfTeams];
        w = new int[numberOfTeams];
        l = new int[numberOfTeams];
        r = new int[numberOfTeams];
        g = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teamName[i] = in.readString();
            teams.put(teamName[i], i);
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
        int teamSquare = numberOfTeams*numberOfTeams;
        int s = teamSquare;
        int t = teamSquare + 1;
        int gameIndex = 0;
        FlowNetwork fn = new FlowNetwork(teamSquare + 2);

        //games to teams
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = 0; j < numberOfTeams; j++) { //game=i*j+j, team1=i, team2=j?
                if (i != j) {
                    fn.addEdge(new FlowEdge(s, gameIndex, g[i][j])); //s to games
                    fn.addEdge(new FlowEdge(gameIndex, i, Double.POSITIVE_INFINITY));//game to team1
                    fn.addEdge(new FlowEdge(gameIndex, j, Double.POSITIVE_INFINITY));//game to team2
                    gameIndex++;
                }
            }
        }

        //teams to t
        for (int i = 0; i < numberOfTeams; i++) {
            if (wins(team)+remaining(team)-w[i] < 0) return true; 
            fn.addEdge(new FlowEdge(i+gameIndex, t, wins(team)+remaining(team)-w[i]));
        }
        StdOut.println(fn.toString());
        FordFulkerson ff = new FordFulkerson(fn, s, t);
        //Check that a(R) is greater than the maximum number of games the eliminated team can win
        //how to find out elimination team? minCut?

        return false;
    }

    //subsetRofteamsthateliminatesgiventeam;nullifnoteliminated
    public Iterable<String> certificateOfElimination(String team)
    {
        int teamSquare = numberOfTeams*numberOfTeams;
        int s = teamSquare;
        int t = teamSquare + 1;
        int gameIndex = 0;
        FlowNetwork fn = new FlowNetwork(teamSquare + 2);

        //games to teams
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = 0; j < numberOfTeams; j++) { //game=i*j+j, team1=i, team2=j?
                if (i != j) {
                    fn.addEdge(new FlowEdge(s, gameIndex, g[i][j])); //s to games
                    fn.addEdge(new FlowEdge(gameIndex, i, Double.POSITIVE_INFINITY));//game to team1
                    fn.addEdge(new FlowEdge(gameIndex, j, Double.POSITIVE_INFINITY));//game to team2
                    gameIndex++;
                }
            }
        }

        //teams to t
        for (int i = 0; i < numberOfTeams; i++) {
            if (wins(team)+remaining(team)-w[i] < 0) return new Queue<String>(); 
            fn.addEdge(new FlowEdge(i+gameIndex, t, wins(team)+remaining(team)-w[i]));
        }
        StdOut.println(fn.toString());
        FordFulkerson ff = new FordFulkerson(fn, s, t);
        //Check that a(R) is greater than the maximum number of games the eliminated team can win
        //how to find out elimination team? minCut?

        Queue<String> result = new Queue<String>();
        for (int i = 0; i < numberOfTeams; i++) {
            if(ff.inCut(i)) {
                result.enqueue(teamName[i]);
            } 
        }
        return result;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]); 
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { "); 
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " "); 
                }
                StdOut.println("}"); 
            }
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
