package Geocoding.ImitationLearning;

import Geocoding.Main.Example;
import Geocoding.Main.Token;
import Geocoding.NaiveBayes.ImitationLearningClassifier;

import java.util.ArrayList;

/**
 * Created by alistair on 26/04/2017.
 */
public class LearnedPolicy extends Policy {

    @Override
    boolean predict(ArrayList<Token> tokens, int currentPosition, ArrayList<Boolean> previousActions) {
        return false;
    }

    ImitationLearningClassifier classifier;

}
