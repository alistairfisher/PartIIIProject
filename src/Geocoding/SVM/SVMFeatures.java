package Geocoding.SVM;

import Geocoding.Main.Example;
import Geocoding.Main.Token;
import Geocoding.PosTags.PosTag;

import java.util.ArrayList;

/**
 * Created by alistair on 26/04/2017.
 */
public class SVMFeatures {

    String word;
    String precedingWord;
    String followingWord;

    PosTag posTag;
    PosTag precedingPosTag;
    PosTag followingPosTag;

    boolean namedEntity;

    int position;

    SVMFeatures(Example example, int position) {
        ArrayList<Token> article = example.articles.get(0);
        if (position > 0) {
            Token preceding = article.get(position - 1);
            this.precedingWord = preceding.word;
            this.precedingPosTag = preceding.posTag;
        }
        if (position < (article.size()-1)) {
            Token following = article.get(position+1);
            this.followingWord = following.word;
            this.followingPosTag = following.posTag;
        }
        Token main = article.get(position);
        this.word = main.word;
        this.posTag = main.posTag;
        this.namedEntity = main.namedEntity;
        this.position = position;
    }

}
