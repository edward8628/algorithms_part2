import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private Digraph G;
    private int[] cache;
    private int[] cacheIterable;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        if (G == null) throw new java.lang.NullPointerException();
        this.G = new Digraph(G); // new G?
        this.cache = new int[4]; //0=v, 1=w, 2=ancestor,3=length
        this.cacheIterable = new int[2]; //0=shortest, 1=ancestor
    }

    // length of shortest ancestral path between v and w;
    public int length(int v, int w)
    {
        this.ancestor(v, w);
        return cache[3];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path;
    public int ancestor(int v, int w)
    {
        if (v < 0 || v > G.V()-1) throw new java.lang.IndexOutOfBoundsException();
        if (w < 0 || w > G.V()-1) throw new java.lang.IndexOutOfBoundsException();
        if (cache[0] == v && cache[1] == w || cache[1] == v && cache[0] == w) return cache[2];

        //read in G again and again?
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(v);

        while (!q.isEmpty()) {
            int i = q.dequeue();
            for (int j : this.G.adj(i)) q.enqueue(j);
            if (bfsW.hasPathTo(i)) {
                cache[0] = v;
                cache[1] = w;
                cache[2] = i;
                cache[3] = bfsV.distTo(i) + bfsW.distTo(i);
                return cache[2];
            }
        }

        //has no path
        cache[0] = v;
        cache[1] = w;
        cache[2] = -1;
        cache[3] = -1;
        return cache[2];
    }

    // length of shortest ancestral between any vertex in v and any vertex in w;
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null) throw new java.lang.NullPointerException();
        for (int i : v) {
            if (i < 0 || i > G.V()-1) throw new java.lang.IndexOutOfBoundsException();
        }
        for (int  i: w) {
            if (i < 0 || i > G.V()-1) throw new java.lang.IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);

        Queue<Integer> q = new Queue<Integer>();
        cacheIterable[0] = Integer.MAX_VALUE; //shortest
        cacheIterable[1] = Integer.MAX_VALUE; //ancestor

        for (int i : v) {
            q.enqueue(i);
        }

        while (!q.isEmpty()) {
            int i = q.dequeue();
            for (int j : this.G.adj(i)) q.enqueue(j);
            if (bfsW.hasPathTo(i)) {
                int length = bfsV.distTo(i) + bfsW.distTo(i);
                if (length < cacheIterable[0]) {
                    cacheIterable[1] = i;
                    cacheIterable[0] = length;
                }
            }
        }

        //has no such path
        if (cacheIterable[0] == Integer.MAX_VALUE) {
            cacheIterable[0] = -1;
            cacheIterable[1] = -1;
        }
        return cacheIterable[0];
    }

    // a common ancestor that participates in shortest ancestral path;
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        //what about cache?
        length(v, w);
        return cacheIterable[1];
    }

    // do unit testing of this class
    public static void main(String[] args) { 
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
    }
}