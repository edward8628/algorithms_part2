import java.util.ArrayList;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
    private SAP sap;
    private final Digraph graph;
    private final SeparateChainingHashST<String, LinkedList<Integer>> nouns;
    private final ArrayList<String> orderByIndex;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) 
            throw new java.lang.NullPointerException();

        nouns = new SeparateChainingHashST<String, LinkedList<Integer>>();
        orderByIndex = new ArrayList<String>();

        //process of reading in synsets as simple hash table
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line[] = in.readLine().split(",");
            String lineNouns[] = line[1].split(" ");//0=index,1=nouns,2=defination
            int index = Integer.parseInt(line[0]);
            orderByIndex.add(line[1]);//key=index, value=noun
            for (int i = 0; i < lineNouns.length; i++) {
                if (nouns.contains(lineNouns[i])) {
                    LinkedList list = nouns.get(lineNouns[i]);
                    list.add(index);
                    nouns.put(lineNouns[i], list); //key=noun,value=list of index
                }
                else {
                    LinkedList list = new LinkedList<Integer>();
                    list.add(index);
                    nouns.put(lineNouns[i], list);
                }
            }
        }

        //process of reading in hypernyms as digraph
        this.graph = new Digraph(orderByIndex.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] temp = in.readLine().split(",");
            for (int i = 1; i < temp.length; i++) {
                //v->w
                graph.addEdge(Integer.parseInt(temp[0]), Integer.parseInt(temp[i]));
            }
        }

        isRootedDAG(this.graph);
        sap = new SAP(this.graph);
    }

    private void isRootedDAG(Digraph graph) {
        DirectedCycle cycleFinder = new DirectedCycle(graph);
        if (cycleFinder.hasCycle()) {
            throw new java.lang.IllegalArgumentException("has a cycle");
        }
        int numberOfZeroOut = 0;
        for (int i = 0; i < graph.V(); i++) {
            int count = 0;
            for (int adjNode : graph.adj(i)) {
                count++;
            }
            if (count == 0) {
                numberOfZeroOut++;
            }
            if (numberOfZeroOut > 1) {
                throw new java.lang.IllegalArgumentException("multiple roots");
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return nouns.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) 
            throw new java.lang.IllegalArgumentException();
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common 
    // ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) 
            throw new java.lang.IllegalArgumentException();
        int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        if (ancestor == -1) return null; // no such path
        return orderByIndex.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);

        //         //test iterable
        //         for (String noun : wordnet.nouns()) {
        //             StdOut.println(noun);
        //         }

        //i need to make a loop to test with all data set
        //test isNoun
        StdOut.println("a is " + wordnet.isNoun("a"));
        StdOut.println("b is " + wordnet.isNoun("b"));
        StdOut.println("C-reactive_protein is " + wordnet.isNoun("C-reactive_protein"));
        StdOut.println("whopper is " + wordnet.isNoun("whopper"));

        //test sap
        StdOut.println(wordnet.sap("zymase", "whacker")); // 83 and 84

        //test length
        StdOut.println(wordnet.distance("zymase", "whacker"));
    }

}