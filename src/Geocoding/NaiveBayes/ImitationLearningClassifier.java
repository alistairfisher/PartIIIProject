

package Geocoding.NaiveBayes;

import Geocoding.Main.Example;
import Geocoding.Main.LocationType;
import Geocoding.Main.Token;
import Geocoding.NER.NERTag;
import Geocoding.PosTags.PosTag;

import java.util.ArrayList;


public abstract class ImitationLearningClassifier {

    int positiveTotal;
    double positivePrior;
    int negativeTotal;
    double negativePrior;

    double[] positiveFeatures;
    double[] negativeFeatures;

    boolean trained = false;

    String name;

    void setUpVectors(int featuresSize, String name) {
        positiveFeatures = new double[featuresSize];
        negativeFeatures = new double[featuresSize];
        this.name = name;
    }

    abstract boolean checkToken(Token t);

    static ArrayList<Token> getTokens(ArrayList<Example> examples) {
        ArrayList<Token> result = new ArrayList<>();
        for (Example e : examples) {
            result.addAll(e.articles.get(0));
        }
        return result;
    }

    void getPriors(ArrayList<Token> tokens) {
        int positive = 0;
        int negative = 0;
        for (Token token : tokens) {
            if (checkToken(token)) {
                positive++;
            } else negative++;
        }
        positiveTotal = positive;
        negativeTotal = negative;
        positivePrior = ((double) positive) / tokens.size();
        negativePrior = 1 - positivePrior;
        System.out.println(positivePrior);
    }

    boolean checkLexicalFeature(ArrayList<Token> tokens, int position, int offset, String word) {
        if ((position + offset < 0) || (position + offset == tokens.size())) return false;
        else return tokens.get(position + offset).word.toLowerCase().equals(word.toLowerCase());
    }

    void updateFeature(int index, boolean positive) {
        if (positive) positiveFeatures[index]++;
        else negativeFeatures[index]++;
    }

    abstract void setFeatures(ArrayList<Token> tokens);

    public void trainClassifier(ArrayList<Example> trainingData) {
        ArrayList<Token> tokens = getTokens(trainingData);
        getPriors(tokens);
        setFeatures(tokens);
        trained = true;
        //for (int i = 0; i < positiveFeatures.length; i++) {
        //    System.out.printf("%f : %f\n", positiveFeatures[i], negativeFeatures[i]);
        //}
    }

    boolean classifyExample(boolean[] features, boolean proportional) {
        double positiveProb;
        if (proportional) {
            positiveProb = positivePrior;
        } else {
            positiveProb = 0.5;
        }
        double negativeProb = 1 - positiveProb;
        for (int i = 0; i < features.length; i++) {
            if (features[i]) {
                positiveProb *= positiveFeatures[i];
                negativeProb *= negativeFeatures[i];
            } else {
                positiveProb *= (1 - positiveFeatures[i]);
                negativeProb *= (1 - negativeFeatures[i]);
            }
        }
        return (positiveProb > negativeProb);

    }

    abstract boolean[] extractFeatures(ArrayList<Token> tokens, int i);

    boolean bannedWord(String s) {
        String[] bannedWords = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday",
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
                "November", "December", "Fire", "Department", "Chief", "Emergency", "County"};
        for (String s1 : bannedWords) {
            if (s1.toLowerCase().equals(s.toLowerCase())) return true;
        }
        return false;
    }

    public boolean classifyExample(ArrayList<Token> tokens, int i, boolean proportional) {
        if (!trained) {
            System.out.println("Not trained");
            return false;
        } else if (bannedWord(tokens.get(i).word)) {
            return false;
        } else {
            boolean[] features = extractFeatures(tokens, i);
            return classifyExample(features, proportional);
        }
    }

    public ArrayList<String> classifyExample(ArrayList<Token> tokens, boolean proportional) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (classifyExample(tokens,i,proportional)) result.add(tokens.get(i).word);
        }
        return result;
    }

    public void trainAndTest(ArrayList<Example> training, ArrayList<Example> test) {
        trainClassifier(training);
        double correct = 0;
        double posCorrect = 0;
        int posTotal = 0;
        int posAttempt = 0;
        int total = 0;
        for (Example example : test) {
            System.out.println(example.labels.bestGeopoint.name);
            ArrayList<Token> tokens = example.articles.get(0);
            for (int i = 0; i < tokens.size(); i++) {
                boolean prediction = classifyExample(tokens, i, true);
                boolean tru = checkToken(tokens.get(i));
                total++;
                if (prediction == tru) {
                    correct++;
                    if (tru) posCorrect++;
                }
                if (tru) posTotal++;
                if (prediction) {
                    posAttempt++;
                }
            }
        }
        double recall = posCorrect / posTotal;
        double precision = posCorrect / posAttempt;
        System.out.println(name);
        System.out.printf("Recall: %f, \"Precision: %f\n", recall, precision);
        System.out.printf("F1: %f, Accuracy: %f\n", 2 * (precision * recall) / (precision + recall), correct/total);
    }


}
