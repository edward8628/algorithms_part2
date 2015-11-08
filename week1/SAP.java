public class SAP {
    Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)

    {
        //if () throw new java.lang.NullPointerException();
        //if () throw new java.lang.IndexOutOfBoundsException(); //if vertex is not between 0 and G.V() - 1.
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)

    {
        //if () throw new java.lang.NullPointerException();
        //if () throw new java.lang.IndexOutOfBoundsException(); //if vertex is not between 0 and G.V() - 1.

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        //no path and return -1?

        return bfs.distTo(w);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)

    {
        //if () throw new java.lang.NullPointerException();
        //if () throw new java.lang.IndexOutOfBoundsException(); //if vertex is not between 0 and G.V() - 1.

        //pathTo()?

        return 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such pat
    public int length(Iterable<Integer> v, Iterable<Integer> w)

    {
        //if () throw new java.lang.NullPointerException();
        //if () throw new java.lang.IndexOutOfBoundsException(); //if vertex is not between 0 and G.V() - 1.

        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

    {
        //if () throw new java.lang.NullPointerException();
        //if () throw new java.lang.IndexOutOfBoundsException(); //if vertex is not between 0 and G.V() - 1.

        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) { 
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt(); 
            int length =sap.length(v,w); 
            int ancestor = sap.ancestor(v, w);
        }
    }
}