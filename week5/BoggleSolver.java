import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.Queue;

public class BoggleSolver {
    private TrieSET dict;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.) 
    public BoggleSolver(String[] dictionary) {
        this.dict = new TrieSET();
        for (String word : dictionary) {
            this.dict.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new java.lang.NullPointerException();
        Queue<String> validWords = new Queue<String>();

        //run through board
        //add letter to current word
        //Q as QU and all uppercase
        //search word if valid in dict
        //collect valid words

        return null;
    }
    
    private void explore() {
    
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise. 
    // (You can assume the word contains only the uppercase letters A through Z.) 
    public int scoreOf(String word) {
        if (word == null) throw new java.lang.NullPointerException();
        if (dict.contains(word)) {
            switch (word.length()) {
                case 0:
                case 1:
                case 2:
                return 0;
                case 3:
                case 4:
                return 1;
                case 5:
                return 2;
                case 6:
                return 3;
                case 7:
                return 5;
                default:
                return 11;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings(); BoggleSolver solver = new BoggleSolver(dictionary); BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word); 
        }
        StdOut.println("Score = " + score); 
    }
}
