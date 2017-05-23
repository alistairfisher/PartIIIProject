package Geocoding.NaiveBayes;

import Geocoding.Main.LocationType;
import Geocoding.Main.Token;
import Geocoding.NER.NERTag;
import Geocoding.PosTags.PosTag;

import java.util.ArrayList;

/**
 * Created by alistair on 18/05/2017.
 */
public class TownClassifier extends AbstractClassifier {

    int vectorSize = 11;

    @Override
    boolean checkToken(Token t) {
        return t.locationType.equals(LocationType.TOWN);
    }

    @Override
    void setFeatures(ArrayList<Token> tokens) {
        setUpVectors(vectorSize, "Town Classifer");
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
            if (Character.isUpperCase(string.charAt(0))) updateFeature(9,positive);
            if (token.posTag.equals(PosTag.NNP)) updateFeature(10,positive);
        }
        for (int i = 0; i < vectorSize; i++) positiveFeatures[i] = positiveFeatures[i]/positiveTotal;
        for (int i = 0; i < vectorSize; i++) negativeFeatures[i] = negativeFeatures[i]/negativeTotal;
    }

    @Override
    boolean[] extractFeatures(ArrayList<Token> tokens, int i) {
        boolean[] result = new boolean[vectorSize];
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
        result[9] = Character.isUpperCase(t.word.charAt(0));
        result[10] = t.posTag.equals(PosTag.NNP);
        return result;
    }
}
