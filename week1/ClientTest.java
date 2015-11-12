import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

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
        StdOut.println(a + " and " + b + " legnth is " + sap.length(q1, q2));
        StdOut.println(a + " and " + b + " ancestor is " + sap.ancestor(q1, q2));
    }

    public void testSingle(int a, int b, SAP sap) {
        StdOut.println(a + " and " + b + " legnth is " + sap.length(a, b));
        StdOut.println(a + " and " + b + " ancestor is " + sap.ancestor(a, b));
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
