import java.lang.*;
import java.util.*;

public class WordNet {
    Digraph graph;
    SeparateChainingHashST<String, Integer> nouns;
    HashMap<Integer, String> definitions; // do I need this?

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) throw new NullPointerException();

        int size = 0;   //for V in digraph 
        //but I can get rid of this and use table.size?
        nouns = new SeparateChainingHashST<String, Integer>();
        definitions = new HashMap<Integer, String>();

        //process of reading in synsets as simple hash table
        In in = new In(synsets);
        while (in.hasNextLine()) {
            size++;
            String line[] = in.readLine().split(",");//read in line
            String lineNouns[] = line[1].split(" ");//0 is index, 1 is nouns, 2 is defination
            int index = Integer.parseInt(line[0]);
            for (int i = 0; i < lineNouns.length; i++) {
                nouns.put(lineNouns[i], index); // key is noun, value is index
            }
            definitions.put(index, line[2]);
        }

        this.graph = new Digraph(size);

        //process of reading in hypernyms as digraph
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            this.graph = new Digraph(size);
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
        return false;
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

        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        //read in as string
        WordNet wordnet = new WordNet(args[0], args[1]);
        //test case?
        for (String noun : wordnet.nouns()) {
            StdOut.println(noun);
        }
    }
}