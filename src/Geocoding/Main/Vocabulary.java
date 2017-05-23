package Geocoding.Main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alistair on 27/04/2017.
 */
public class Vocabulary {

    static HashMap<String, Integer> vocabulary = new HashMap<>();

    static int size() {
        return vocabulary.size();
    }

    static HashMap<String, Integer> counts = new HashMap<>();

    static void processToken(Token token) {
        String word = token.word;
        if (vocabulary.containsKey(word)) {
            counts.put(word, counts.get(word)+1);
        }
        else {
            vocabulary.put(word,size());
            counts.put(word,1);
        }
    }

    static void processExample(Example e) {
        ArrayList<Token> tokens = e.articles.get(0);
        for (Token t: tokens) {
            processToken(t);
        }
    }

    static void processExamples(ArrayList<Example> examples) {
        for (Example e: examples) {
            processExample(e);
        }
        System.out.println(Vocabulary.size());
        int x = 0;
        for (String s: counts.keySet()) {
            if (counts.get(s) > 5) vocabulary.remove(s);
        }
    }

}
