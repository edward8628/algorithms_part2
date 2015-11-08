import java.lang.*;
import java.util.*;

public class WordNet {
    final Digraph graph;
    final SeparateChainingHashST<String, LinkedList<Integer>> nouns;
    final HashMap<Integer, String> definitions; // do I need this?

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) throw new NullPointerException();

        int size = 0;   //for V in digraph 
        //but I can get rid of this and use table.size?
        nouns = new SeparateChainingHashST<String, LinkedList<Integer>>();
        definitions = new HashMap<Integer, String>();

        //process of reading in synsets as simple hash table
        In in = new In(synsets);
        while (in.hasNextLine()) {
            size++;
            String line[] = in.readLine().split(",");//read in line
            String lineNouns[] = line[1].split(" ");//0 is index, 1 is nouns, 2 is defination
            int index = Integer.parseInt(line[0]);
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
            definitions.put(index, line[2]); //do I need this?
        }

        this.graph = new Digraph(size);

        //process of reading in hypernyms as digraph
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] temp = in.readLine().split(",");
            for (int i = 1; i < temp.length; i++) {
                try {
                    //what the hell root?
                    //what if it is not number?
                    graph.addEdge(Integer.parseInt(temp[0]), Integer.parseInt(temp[i])); //v->w
                } catch (IndexOutOfBoundsException e) {
                    StdOut.println(e);
                    //if the input does not correspond to a rooted DAG
                    throw new IllegalArgumentException();
                }
            }

        }
    }

    private void validateIndex (String str) {

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

        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        //only for testing
        for (int i : nouns.get(nounA)) {
            StdOut.println(nounA + " " + i);
        }
        for (int i : nouns.get(nounB)) {
            StdOut.println(nounB + " " + i);
        }
        
        //SAP sap = new SAP(nouns.get(nounA), nouns.get(nounB));

        return null;
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