import java.lang.*;
import java.util.*;

public class WordNet {
    Digraph graph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) throw new NullPointerException();
        //if (true) throw new IllegalArgumentException();
        //use split to separate comma?semicolon?or space
        //use Integer.parseInt() to parse string as int
        //readline to parse text string

        int size = 0;   //for V in digraph //but I can get rid of this and use table.size

        //process of reading in synsets as simple table
        In in = new In(synsets);
        while (in.hasNextLine()) {
            size++;
            in.readLine();
        }

        this.graph = new Digraph(size);

        //process of reading in hypernyms as digraph
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            this.graph = new Digraph(size);
            String[] temp = in.readLine().split(",");
            //some lines have mnore than 2 number
            for (int i = 1; i < temp.length; i++) {
                graph.addEdge(Integer.parseInt(temp[0]), Integer.parseInt(temp[i])); //v->w
            }

            
        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return null;
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
    }
}