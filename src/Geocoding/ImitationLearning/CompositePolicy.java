package Geocoding.ImitationLearning;

import Geocoding.Main.Example;
import Geocoding.Main.Token;

import java.util.ArrayList;

/**
 * Created by alistair on 31/03/2017.
 */
class CompositePolicy extends Policy {

    Policy learned;
    Policy expert;
    Double p;

    public CompositePolicy(Policy learned, Policy expert, Double p) {
        this.learned = learned;
        this.expert = expert;
        this.p = p;
    }

    @Override
    boolean predict(ArrayList<Token> tokens, int currentPosition, ArrayList<Boolean> previousActions) {
        double rand = Math.random();
        if (rand < p) return expert.predict(tokens,currentPosition,previousActions);
        else return learned.predict(tokens,currentPosition,previousActions);
    }
}
