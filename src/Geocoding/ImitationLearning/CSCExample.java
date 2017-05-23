package Geocoding.ImitationLearning;

import Geocoding.Main.Example;
import Geocoding.Main.Token;

import java.util.ArrayList;

/**
 * Created by alistair on 20/05/2017.
 */
public class CSCExample {

    int index;

    ArrayList<Boolean> actions;

    ArrayList<Token> tokens;

    int[] costs;

    public CSCExample(int index, ArrayList<Boolean> actions, ArrayList<Token> tokens, int[] costs) {
        this.index = index;
        this.actions = actions;
        this.tokens = tokens;
        this.costs = costs;
    }

    boolean label;

}
