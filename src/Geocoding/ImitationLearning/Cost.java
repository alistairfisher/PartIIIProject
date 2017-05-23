package Geocoding.ImitationLearning;

import Geocoding.Main.Example;
import Geocoding.Main.Token;

import java.util.ArrayList;

/**
 * Created by alistair on 22/05/2017.
 */
public class Cost {

    static int hammingCost(ArrayList<Token> tokens, ArrayList<Boolean> actions) throws Exception {
        int cost = 0;
        if (tokens.size() != actions.size()) {
            throw new Exception();
        }
        for (int i = 0; i < actions.size(); i++) {
            if (tokens.get(i).addressSegment != actions.get(i)) cost++;
        }
        return cost;
    }

}
