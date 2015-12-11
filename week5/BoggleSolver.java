import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.TST;
//https://github.com/vinsonlee/coursera/blob/master/algs4partII-004/boggle/BoggleSolver.java
//https://github.com/lcanet/coursera/blob/master/algs4p2/src/BoggleSolver.java
public class BoggleSolver {
    private final BoggleDictionary dict;

    private class DictionaryNode {
        private char c; // character
        private DictionaryNode left, mid, right; // left, middle, and right subtries
        private String val; // value associated with string
    }

    private class BoggleDictionary {
        private DictionaryNode root;

        public void add(String s) {
            root = put(root, s, s, 0);
        }

        private DictionaryNode put(DictionaryNode x, String s, String val, int d) {
            char c = s.charAt(d);
            if (x == null) {
                x = new DictionaryNode();
                x.c = c;
            }
            if (c < x.c)
                x.left = put(x.left, s, val, d);
            else if (c > x.c)
                x.right = put(x.right, s, val, d);
            else if (d < s.length() - 1)
                x.mid = put(x.mid, s, val, d + 1);
            else
                x.val = val;
            return x;
        }

        private boolean contains(String key) {
            return get(key) != null;
        }

        private String get(String key) {
            if (key == null)
                throw new NullPointerException();
            if (key.length() == 0)
                throw new IllegalArgumentException("key must have length >= 1");
            DictionaryNode x = get(root, key, 0);
            if (x == null)
                return null;
            return x.val;
        }

        // return subtrie corresponding to given key
        private DictionaryNode get(DictionaryNode x, String key, int d) {
            if (x == null)
                return null;
            char c = key.charAt(d);
            if (c < x.c)
                return get(x.left, key, d);
            else if (c > x.c)
                return get(x.right, key, d);
            else if (d < key.length() - 1)
                return get(x.mid, key, d + 1);
            else
                return x;
        }

        public boolean hasPrefix(String query) {
            DictionaryNode x = get(root, query, 0);
            return x != null;
        }
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.) 
    public BoggleSolver(String[] dictionary) {
        StdRandom.shuffle(dictionary);
        this.dict = new BoggleDictionary();
        for (String word : dictionary) {
            this.dict.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new java.lang.NullPointerException();
        SET<String> validWords = new SET<String>();

        //run through board
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                explore(board, i, j, marked, "", validWords);
            }
        }

        return validWords;
    }

    private void explore(BoggleBoard board, int row, int col, boolean[][] marked, String prefix, SET<String> set) {
        if (marked[row][col]) {
            return;
        }

        //add letter to current word
        char letter = board.getLetter(row, col);
        String word = prefix;
        //Q as QU and all uppercase
        if (letter == 'Q') {
            word += "QU";
        } else {
            word += letter;
        }
        //check if this word has prefix in our dict to save time
        if (!dict.hasPrefix(word)) {
            return;
        }
        //search word if valid in dict
        if (word.length() > 2 && dict.contains(word)) {
            set.add(word);
        }
        marked[row][col] = true;

        //collect valid words
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if ((row + i >= 0) && (row + i < board.rows()) && (col + j >= 0) && (col + j < board.cols())) {
                    explore(board, row + i, col + j, marked, word, set);
                }
            }
        }
        marked[row][col] = false;
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
        } else 
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
