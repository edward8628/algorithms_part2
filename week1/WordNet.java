import java.lang.*;
import java.util.*;

public class WordNet {
    SAP sap;
    final Digraph graph;
    final SeparateChainingHashST<String, LinkedList<Integer>> nouns;
    final ArrayList<String> orderByIndex;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) throw new NullPointerException();

        int size = 0;   //for V in digraph 
        //but I can get rid of this and use table.size?
        nouns = new SeparateChainingHashST<String, LinkedList<Integer>>();
        orderByIndex = new ArrayList<String>();

        //process of reading in synsets as simple hash table
        In in = new In(synsets);
        while (in.hasNextLine()) {
            size++;
            String line[] = in.readLine().split(",");//read in line and split by comma
            String lineNouns[] = line[1].split(" ");//0 is index, 1 is nouns, 2 is defination
            int index = Integer.parseInt(line[0]);
            orderByIndex.add(line[1]);
            for (int i = 0; i < lineNouns.length; i++) {
                if (nouns.contains(lineNouns[i])) {
                    LinkedList list = nouns.get(lineNouns[i]);
                    list.add(index);
                    nouns.put(lineNouns[i], list); // key is noun, value is list of index
                }
                else {
                    LinkedList list = new LinkedList<Integer>();
                    list.add(index);
                    nouns.put(lineNouns[i], list);
                }
            }
        }

        //process of reading in hypernyms as digraph
        this.graph = new Digraph(size);
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] temp = in.readLine().split(",");
            for (int i = 1; i < temp.length; i++) {
                try {
                    //what the hell is root?
                    //what if it is not number?
                    graph.addEdge(Integer.parseInt(temp[0]), Integer.parseInt(temp[i])); //v->w
                } catch (IndexOutOfBoundsException e) {
                    //if the input does not correspond to a rooted DAG
                    StdOut.println(e); // but is this right?
                    throw new IllegalArgumentException();
                }
            }
        }
        
        
        //save the graph as 
        sap = new SAP(this.graph);
    }

    private void validateIndex (String str) {
        //do I need this for try and catch?
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
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        
        int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        if (ancestor == -1) return null; // no such path
        
        return orderByIndex.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);

        //test iterable
        for (String noun : wordnet.nouns()) {
            StdOut.println(noun);
        }

        //test isNoun
        StdOut.println("a is " + wordnet.isNoun("a"));
        StdOut.println("b is " + wordnet.isNoun("b"));
        StdOut.println("C-reactive_protein is " + wordnet.isNoun("C-reactive_protein"));
        StdOut.println("whopper is " + wordnet.isNoun("whopper"));

        //test sap
        wordnet.sap("thing", "thromboplastin"); // 83 and 84
    }
}