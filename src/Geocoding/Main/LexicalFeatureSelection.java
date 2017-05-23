package Geocoding.Main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alistair on 13/05/2017.
 */
public class LexicalFeatureSelection {

    private static HashMap<String, Integer> counts = new HashMap<>();

    static int total;

    private static void countString(String s) {
        if (s.length() ==  1) return;
        if (counts.keySet().contains(s)) {
            counts.put(s,counts.get(s)+1);
        }
        else counts.put(s,1);
    }

    private static int biggestCount() {
        int biggest = 0;
        for (String s: counts.keySet()) {
            if (counts.get(s) > biggest) {
                biggest = counts.get(s);
            }
        }
        return biggest;
    }

    private static void extractString() {
        ArrayList<String> result = new ArrayList<>();
        int current = biggestCount();
        while (result.size()<10) {
            for (String s: counts.keySet()) {
                if (counts.get(s) == current){
                    result.add(s);
                    System.out.println(s);
                    System.out.println(current);
                }
            }
            current--;
        }
        System.out.println(total);
    }

    static void parseExamples(ArrayList<Example> examples) {
        for (Example example: examples) {
            ArrayList<Token> tokens = example.articles.get(0);
            for (int i = 0; i < tokens.size();i++) {
                if (tokens.get(i).locationType.equals(LocationType.STREET)) {
                    countString(tokens.get(i).word);
                    System.out.println(tokens.get(i).word);
                    total++;
                }
            }
        }
        extractString();
    }

    /*
    in
775
the
591
of
483
on
443
at
256
and
177
from
147
to
144
The
111
near
106

in
735
the
591
of
436
on
427
at
238
and
162
from
126
to
112
The
111
near
105

2 before

in
405
fire
322
at
259
on
238
of
235
block
193
to
162
the
154
home
115
Road
102

1 after

2267
Road
564
Fire
453
Street
378
Avenue
185
block
173
Drive
156
and
126
St.
102
fire
98
firefighters



     */

}
