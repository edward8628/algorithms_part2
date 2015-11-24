import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class ClientTest
{
    public static void main(String[] args) {
        BaseballElimination be = new BaseballElimination(args[0]);
        for (String team : be.teams()) {
            StdOut.println(team);
        }
    }
}
