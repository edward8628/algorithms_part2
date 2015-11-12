import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Write a description of class ClientTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ClientTest
{

    public void testIterable(int a, int b, SAP sap) {
        Queue<Integer> q1 = new Queue<Integer>();
        q1.enqueue(a);
        Queue<Integer> q2 = new Queue<Integer>();
        q2.enqueue(b);

        Stopwatch lengthWatch = new Stopwatch(); 
        int length = sap.length(q1, q2);
        double lengthTime = lengthWatch.elapsedTime();

        Stopwatch ancestorWatch = new Stopwatch(); 
        int ancestor = sap.ancestor(q1, q2);
        double ancestorTime = ancestorWatch.elapsedTime();

        StdOut.println(a + " and " + b + " legnth is " + length + " time is " + lengthTime);
        StdOut.println(a + " and " + b + " ancestor is " + ancestor + " time is " + ancestorTime);
    }

    public void testSingle(int a, int b, SAP sap) {
        Stopwatch lengthWatch = new Stopwatch(); 
        int length = sap.length(a, b);
        double lengthTime = lengthWatch.elapsedTime();

        Stopwatch ancestorWatch = new Stopwatch(); 
        int ancestor = sap.ancestor(a, b);
        double ancestorTime = ancestorWatch.elapsedTime();
        
        StdOut.println(a + " and " + b + " legnth is " + length + " time is " + lengthTime);
        StdOut.println(a + " and " + b + " ancestor is " + ancestor + " time is " + ancestorTime);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        ClientTest test = new ClientTest();

        test.testIterable(Integer.parseInt(args[1]), Integer.parseInt(args[2]), sap);
        test.testSingle(Integer.parseInt(args[1]), Integer.parseInt(args[2]), sap);
    }
}
