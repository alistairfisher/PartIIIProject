package Geocoding.NaiveBayes;

import Geocoding.Main.LocationType;
import Geocoding.Main.Token;
import Geocoding.NER.NERTag;
import Geocoding.PosTags.PosTag;

import java.util.ArrayList;

/**
 * Created by alistair on 17/05/2017.
 */
public class StreetClassifier extends AbstractClassifier {

    int vectorSize = 17;

    @Override
    boolean checkToken(Token t) {
        return t.locationType.equals(LocationType.STREET);
    }

    @Override
    void setFeatures(ArrayList<Token> tokens) {
        setUpVectors(vectorSize, "Street Classifier");
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

}
