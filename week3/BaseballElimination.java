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
        if (!teams.containsKey(team)) throw new java.lang.IllegalArgumentException();
        return w[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team)
    {
        if (!teams.containsKey(team)) throw new java.lang.IllegalArgumentException();
        return l[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team)
    {
        if (!teams.containsKey(team)) throw new java.lang.IllegalArgumentException();
        return r[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        if (!teams.containsKey(team1)) throw new java.lang.IllegalArgumentException();
        if (!teams.containsKey(team2)) throw new java.lang.IllegalArgumentException();
        return g[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team)
    {
        if (!teams.containsKey(team)) throw new java.lang.IllegalArgumentException();
        int teamSquare = numberOfTeams*numberOfTeams;
        int s = teamSquare;
        int t = teamSquare + 1;
        int gameIndex = 0;
        double maxCapacity = 0;
        FlowNetwork fn = new FlowNetwork(teamSquare + 2);

        for (int i = 0; i < numberOfTeams; i++) {
            if (wins(team)+remaining(team)-w[i] < 0) {
                return true;
            }
            if (team.equals(teamName[i])) continue;
            fn.addEdge(new FlowEdge(i, t, wins(team)+remaining(team)-w[i]));//teams to t
        }

        //games to teams
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = i+1; j < numberOfTeams; j++) {
                if (j != teams.get(team) && i != teams.get(team)) {
                    maxCapacity += g[i][j];
                    fn.addEdge(new FlowEdge(s, gameIndex+numberOfTeams, g[i][j])); //s to games
                    fn.addEdge(new FlowEdge(gameIndex+numberOfTeams, i, Double.POSITIVE_INFINITY));//game to team1
                    fn.addEdge(new FlowEdge(gameIndex+numberOfTeams, j, Double.POSITIVE_INFINITY));//game to team2
                    gameIndex++;
                }
            }
        }
        FordFulkerson ff = new FordFulkerson(fn, s, t);
        //StdOut.println(fn.toString());
        //StdOut.println("value: " + ff.value() + " max capacity: " +maxCapacity);
        
        if (ff.value() < maxCapacity) {
            return true;
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team)
    {
        if (!teams.containsKey(team)) throw new java.lang.IllegalArgumentException();
        int teamSquare = numberOfTeams*numberOfTeams;
        int s = teamSquare;
        int t = teamSquare + 1;
        int gameIndex = 0;
        double maxCapacity = 0;
        Queue<String> eliminatedTeams = new Queue<String>();
        FlowNetwork fn = new FlowNetwork(teamSquare + 2);

        for (int i = 0; i < numberOfTeams; i++) {
            if (wins(team)+remaining(team)-w[i] < 0) {
                eliminatedTeams.enqueue(teamName[i]);
                continue;
            }
            if (team.equals(teamName[i])) continue;
            fn.addEdge(new FlowEdge(i, t, wins(team)+remaining(team)-w[i]));//teams to t
        }
        if (eliminatedTeams.size() > 0) {
            return eliminatedTeams;
        }

        //games to teams
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = i+1; j < numberOfTeams; j++) {
                if (j != teams.get(team) && i != teams.get(team)) {
                    maxCapacity += g[i][j];
                    fn.addEdge(new FlowEdge(s, gameIndex+numberOfTeams, g[i][j])); //s to games
                    fn.addEdge(new FlowEdge(gameIndex+numberOfTeams, i, Double.POSITIVE_INFINITY));//game to team1
                    fn.addEdge(new FlowEdge(gameIndex+numberOfTeams, j, Double.POSITIVE_INFINITY));//game to team2
                    gameIndex++;
                }
            }
        }
        FordFulkerson ff = new FordFulkerson(fn, s, t);

        Queue<String> result = new Queue<String>();
        for (int i = 0; i < numberOfTeams; i++) {
            if (ff.inCut(i) && i != teams.get(team)) {
                result.enqueue(teamName[i]);
            }
        }
        if (result.size() == 0) return null;
        return result;
    }

    public static void main(String[] args) {
        // % java BaseballElimination teams4.txt
        // Atlanta is not eliminated
        // Philadelphia is eliminated by the subset R = { Atlanta New_York } 
        // New_York is not eliminated
        // Montreal is eliminated by the subset R = { Atlanta }
        //
        // % java BaseballElimination teams5.txt 
        // New_York is not eliminated
        // Baltimore is not eliminated
        // Boston is not eliminated
        // Toronto is not eliminated
        // Detroit is eliminated by the subset R = { New_York Baltimore Boston Toronto }

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
    }
}
