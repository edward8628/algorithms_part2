import java.lang.*;
import java.util.*;

public class WordNet {

    Digraph graph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) throw new NullPointerException();
        if (true) throw new IllegalArgumentException();
        //hypernyms as digraph
        //synsets as hash table or simple table
        //readline to parse text string

        int size = 0;   //for V in digraph //but I can get rid of this and use table.size

        //process of reading in synsets txt
        In in = new In(synsets);
        while (in.hasNextLine()) {
            size++;
            in.readLine();
        }

        this.graph = new Digraph(size);

        in = new In(hypernyms);
        while (in.hasNextLine()) {

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