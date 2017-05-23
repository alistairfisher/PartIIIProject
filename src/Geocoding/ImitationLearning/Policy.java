package Geocoding.ImitationLearning;

import Geocoding.Main.Example;
import Geocoding.Main.Token;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by alistair on 30/03/2017.
 */
public abstract class Policy {

  abstract boolean predict(ArrayList<Token> tokens, int currentPosition, ArrayList<Boolean> previousActions);

  ArrayList<Boolean> predict(ArrayList<Token> tokens, ArrayList<Boolean> actions) {
      for (int i = actions.size(); i < tokens.size(); i++) {
          boolean action = predict(tokens, i, actions);
          actions.add(action);
      }
      return actions;
  }

  ArrayList<Boolean> predict(ArrayList<Token> tokens) {
      return predict(tokens, new ArrayList<>());
  }

  ArrayList<Boolean> completePrediction(ArrayList<Token> tokens, ArrayList<Boolean> actions, boolean first) {
      ArrayList<Boolean> copy = new ArrayList<>();
      copy.addAll(actions);
      copy.add(first);
      return predict(tokens,actions);
  }

}
