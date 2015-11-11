public class Outcast {
    WordNet wordnet;

    // constructor takes a WordNet object 
    public Outcast(WordNet wordnet)
    {
        this.wordnet = wordnet;
    }

    //given an array of WordNet nouns,return an outcast 
    public String outcast(String[] nouns)
    {
        int length = nouns.length;
        int[] dist = new int[length];
        int worst = 0;
        for (int i = 0; i < length; i++) {
            for (String noun : nouns) {
                dist[i] += wordnet.distance(nouns[i], noun);
            }
            if (dist[worst] < dist[i]) {
                worst = i;
            }
        }

        return nouns[worst];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        } 
    }
}
