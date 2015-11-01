
public class TestClient
{
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
