package Geocoding.NaiveBayes;

import Geocoding.Main.Example;
import Geocoding.Main.LocationType;
import Geocoding.Main.Token;
import Geocoding.NER.NERTag;
import Geocoding.PosTags.PosTag;
import sun.plugin.net.protocol.jar.CachedJarURLConnection;

import java.util.ArrayList;

/**
 * Created by alistair on 13/05/2017.
 */
public class Classifier {

    /*private static int positiveTotal;
    private static double positivePrior;
    private static int negativeTotal;
    private static double negativePrior;

    private static double[] positiveFeatures = new double[17];
    private static double[] negativeFeatures = new double[17];

    private static boolean trained = false;

    static boolean checkToken(Token t) {
        return t.locationType.equals(LocationType.STREET);
    }

    static ArrayList<Token> getTokens(ArrayList<Example> examples) {
        ArrayList<Token> result = new ArrayList<>();
        for (Example e: examples) {
            result.addAll(e.articles.get(0));
        }
        return result;
    }

    private static void getPriors(ArrayList<Token> tokens) {
        int positive = 0;
        int negative = 0;
        for (Token token: tokens) {
            if (checkToken(token)) {
                positive++;
            }
            else negative++;
        }
        positiveTotal = positive;
        negativeTotal = negative;
        positivePrior = ((double)positive)/tokens.size();
        negativePrior = 1-positivePrior;
        System.out.println(positivePrior);
    }

    private static boolean checkLexicalFeature(ArrayList<Token> tokens, int position, int offset, String word) {
        if ((position+offset < 0) || (position+offset == tokens.size())) return false;
        else return tokens.get(position+offset).word.toLowerCase().equals(word.toLowerCase());
    }

    private static void updateFeature(int index, boolean positive) {
        if (positive) positiveFeatures[index]++;
        else negativeFeatures[index]++;
    }

    private static void setFeatures(ArrayList<Token> tokens) {
        for (int i = 0;i<tokens.size();i++) {
            Token token = tokens.get(i);
            String string = token.word;
            boolean positive = checkToken(token);
            if (token.namedEntity) {
                if (token.nerTag.equals(NERTag.LOCATION)) updateFeature(0,positive);
                    else if (token.nerTag.equals(NERTag.ORGANIZATION)) updateFeature(1,positive);
                    else updateFeature(2,positive);
                }
            if (token.position < 100) updateFeature(3,positive);
            if (checkLexicalFeature(tokens,i,-2,"fire")) updateFeature(4,positive);
            if (checkLexicalFeature(tokens,i,-1,"in")) updateFeature(5,positive);
            if (checkLexicalFeature(tokens,i,-1,"of")) updateFeature(6,positive);
            if (checkLexicalFeature(tokens,i,-1,"on")) updateFeature(7,positive);
            if (checkLexicalFeature(tokens,i,-1,"at")) updateFeature(8,positive);
            if (checkLexicalFeature(tokens,i,0,"road")) updateFeature(9,positive);
            if (checkLexicalFeature(tokens,i,0,"street")) updateFeature(10,positive);
            if (checkLexicalFeature(tokens,i,0,"avenue")) updateFeature(11,positive);
            if (checkLexicalFeature(tokens,i,1,"road")) updateFeature(12,positive);
            if (checkLexicalFeature(tokens,i,1,"street")) updateFeature(13,positive);
            if (checkLexicalFeature(tokens,i,1,"avenue")) updateFeature(14,positive);
            if (Character.isUpperCase(string.charAt(0))) updateFeature(15,positive);
            if (token.posTag.equals(PosTag.NNP)) updateFeature(16,positive);
        }
        for (int i = 0; i < positiveFeatures.length; i++) positiveFeatures[i] = positiveFeatures[i]/positiveTotal;
        for (int i = 0; i < negativeFeatures.length; i++) negativeFeatures[i] = negativeFeatures[i]/negativeTotal;
    }

    private static void trainClassifier(ArrayList<Example> trainingData) {
        ArrayList<Token> tokens = getTokens(trainingData);
        getPriors(tokens);
        setFeatures(tokens);
        trained = true;
        for (int i = 0; i< positiveFeatures.length; i++) {
            System.out.printf("%f : %f\n",positiveFeatures[i], negativeFeatures[i]);
        }
    }

    private static boolean classifyExample(boolean[] features, boolean proportional) {
        double positiveProb;
        if (proportional) {
            positiveProb = positivePrior;
        }
        else {
            positiveProb = 0.5;
        }
        double negativeProb = 1-positiveProb;
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

    private static boolean[] extractFeatures(ArrayList<Token> tokens, int i) {
        boolean[] result = new boolean[17];
        Token t = tokens.get(i);
        if (t.namedEntity) {
            result[0] = t.nerTag.equals(NERTag.LOCATION);
            result[1] = t.nerTag.equals(NERTag.ORGANIZATION);
            result[2] = t.nerTag.equals(NERTag.PERSON);
        }
        else result[0] = false;
        result[3] = t.position<100;
        result[4] = checkLexicalFeature(tokens,i,-2,"fire");
        result[5] = checkLexicalFeature(tokens,i,-1,"in");
        result[6] = checkLexicalFeature(tokens,i,-1,"of");
        result[7] = checkLexicalFeature(tokens,i,-1,"on");
        result[8] = checkLexicalFeature(tokens,i,-1,"at");
        result[9] = checkLexicalFeature(tokens,i,0,"road");
        result[10] = checkLexicalFeature(tokens,i,0,"street");
        result[11] = checkLexicalFeature(tokens,i,0,"avenue");
        result[12] = checkLexicalFeature(tokens,i,1,"road");
        result[13] = checkLexicalFeature(tokens,i,1,"street");
        result[14] = checkLexicalFeature(tokens,i,1,"avenue");
        result[15] = Character.isUpperCase(t.word.charAt(0));
        result[16] = t.posTag.equals(PosTag.NNP);
        return result;
    }

    private static boolean bannedWord(String s) {
        String[] bannedWords = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday",
            "January","February","March","April","May","June","July","August","September","October",
            "November","December","Fire","Department","Chief","Emergency","County"};
        for (String s1: bannedWords) {
            if (s1.toLowerCase().equals(s.toLowerCase())) return true;
        }
        return false;
    }

    private static boolean classifyExample(ArrayList<Token> tokens, int i, boolean proportional) {
        if (!trained) {
            System.out.println("Not trained");
            return false;
        }
        else if (bannedWord(tokens.get(i).word)) {
            return false;
        }
        else {
            boolean[] features = extractFeatures(tokens,i);
            return classifyExample(features, proportional);
        }
    }

    public static double trainAndTest(ArrayList<Example> training, ArrayList<Example> test) {
        trainClassifier(training);
        double correct = 0;
        double posCorrect = 0;
        int posTotal = 0;
        int posAttempt = 0;
        int total = 0;
        for (Example example: test) {
            ArrayList<Token> tokens = example.articles.get(0);
            for (int i = 0; i < tokens.size(); i++) {
                boolean prediction = classifyExample(tokens,i, false);
                boolean tru = checkToken(tokens.get(i));
                total++;
                if (prediction == tru) {
                    correct++;
                    if (tru) posCorrect++;
                }
                if (tru) posTotal++;
                if (prediction) posAttempt++;
                if (!prediction) {
                    //if (tru) System.out.println(tokens.get(i).word);
                }
            }
        }
        double recall = posCorrect/posTotal;
        double precision = posCorrect/posAttempt;
        System.out.printf("Recall: %f\n",recall);
        System.out.printf("Precision: %f\n",(precision));
        System.out.printf("F1: %f\n",2*(precision*recall)/(precision+recall));
        System.out.printf("Accuracy: %f\n",(correct/total));
        return correct/total;
    }
    /*
.2 prior
Recall: 0.629492
Precision: 0.251278
F1: 0.359180
Accuracy: 0.960497

real prior
Recall: 0.258984
Precision: 0.529561
F1: 0.347850
Accuracy: 0.982922

uniform

Recall: 0.820735
Precision: 0.143424
F1: 0.244178
Accuracy: 0.910642

NNP

Recall: 0.808957
Precision: 0.182709
F1: 0.298092
Accuracy: 0.931846


     */

}
