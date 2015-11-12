import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

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
        if (cache[0]==v && cache[1]==w || cache[1]==v && cache[0]==w) return cache[2];

        //read in G again and again?
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(this.G, w);
        int counter = 0;

        while (this.G.outdegree(v) != 0) {//if at root
            if (bfs.hasPathTo(v)) {//ancestor is itself
                cache[0] = v;
                cache[1] = w;
                cache[2] = v;
                cache[3] = counter + bfs.distTo(v);
                return cache[2];
            }
            for (int i : this.G.adj(v)) {
                counter++;
                if (bfs.hasPathTo(i)) {
                    cache[0] = v;
                    cache[1] = w;
                    cache[2] = i;
                    cache[3] = counter + bfs.distTo(i);
                    return cache[2];
                }
                v = i;
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

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(this.G, w);
        cacheIterable[0] = Integer.MAX_VALUE; //shortest
        cacheIterable[1] = Integer.MAX_VALUE; //ancestor

        for (int i : v) {
            int counter = 0;
            if (bfs.hasPathTo(i)) {//ancestor is itself
                int length = counter + bfs.distTo(i);
                if (length < cacheIterable[0]) {
                    cacheIterable[1] = i;
                    cacheIterable[0] = length;
                    continue;
                }
            }
            while (this.G.outdegree(i) != 0) {//if at root
                for (int j : this.G.adj(i)) {
                    counter++;
                    if (bfs.hasPathTo(j)) {
                        //see if shorter
                        int length = counter + bfs.distTo(j);
                        if (length < cacheIterable[0]) {
                            cacheIterable[1] = j;
                            cacheIterable[0] = length;
                        }
                    }
                    i = j;
                }
            }
            if (cacheIterable[0] == 0) break; //best solution
        }

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
        //not test yet
        length(v, w);
        return cacheIterable[1];
    }

    // do unit testing of this class
    public static void main(String[] args) { 
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        //test single length and ancestor
        for (int v = 0; v < G.V(); v++) {
            for (int w = 0; w < G.V(); w++){
                StdOut.println(v + " and " + w + " legnth is " + sap.length(v,w));
                StdOut.println(v + " and " + w + " ancestor is " + sap.ancestor(v,w));
            }
        }

    }
}