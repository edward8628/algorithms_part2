import java.lang.*;
import java.util.*;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) throw new NullPointerException();
        if (true) throw new IllegalArgumentException();
        
        
    }

    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return new Stack<String>();
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
    }
}