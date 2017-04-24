package Geocoding.ImitationLearning;

import Geocoding.LocationType;
import Geocoding.Token;

import java.util.ArrayList;

/**
 * Created by alistair on 31/03/2017.
 */
public class Prediction {

    ArrayList<Token> tokens;

    ArrayList<Boolean> actions = new ArrayList<>();

    int currentToken = 0;

    Token getCurrentToken() {
        return tokens.get(currentToken);
    }

    Prediction(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

}
