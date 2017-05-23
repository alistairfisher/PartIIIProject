package Geocoding.ImitationLearning;

import Geocoding.Main.Example;
import Geocoding.Main.Token;

import java.util.ArrayList;

/**
 * Created by alistair on 26/04/2017.
 */
public class ExpertPolicy extends Policy {

    ArrayList<Token> tokens;

    ExpertPolicy(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    boolean predict(ArrayList<Token> tokens, int currentPosition, ArrayList<Boolean> previousActions) {
        return tokens.get(currentPosition).addressSegment;
    }
}
