public class SAP {
    Digraph G;
    int[] cache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        if (G == null) throw new java.lang.NullPointerException();
        this.G = new Digraph(G); // new G?
        this.cache = new int[4]; //0=v, 1=w, 2=ancestor,3=length
    }

    // length of shortest ancestral path between v and w;
    public int length(int v, int w)
    {
        ancestor(v, w);
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

        if (bfs.hasPathTo(v)) {//ancestor is itself
            cache[0] = v; //does this work?
            cache[1] = w;
            cache[2] = v;
            cache[3] = counter + bfs.distTo(v);
            return cache[2];
        }

        while (this.G.outdegree(v) != 0) {//if at root
            for (int i : this.G.adj(v)) {
                counter++;
                if (bfs.hasPathTo(i)) {
                    cache[0] = v; //does this work?
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

    // length of shortest ancestral between any vertex in v and any vertex in w; -1 if no such pat
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null) throw new java.lang.NullPointerException();
        //if () throw new java.lang.IndexOutOfBoundsException(); //if vertex is not between 0 and G.V() - 1.

        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null) throw new java.lang.NullPointerException();
        //if () throw new java.lang.IndexOutOfBoundsException(); //if vertex is not between 0 and G.V() - 1.

        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) { 
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        //int v = Integer.parseInt(args[1]);
        //int w = Integer.parseInt(args[2]);

        //test single length and ancestor
        for (int v = G.V()-1; v >= 0; v--) {
            for (int w = G.V()-1; w >= 0; w--) {
                StdOut.println(v + " and " + w + " legnth is " + sap.length(v,w));
                StdOut.println(v + " and " + w + " ancestor is " + sap.ancestor(v,w));
            }
        }
        int v = 3;
        int w = 11; 
        StdOut.println(v + " and " + w + " legnth is " + sap.length(v,w));
        StdOut.println(v + " and " + w + " ancestor is " + sap.ancestor(v,w));
        v = 11;
        w = 3; 
        StdOut.println(v + " and " + w + " legnth is " + sap.length(v,w));
        StdOut.println(v + " and " + w + " ancestor is " + sap.ancestor(v,w));
    }
}