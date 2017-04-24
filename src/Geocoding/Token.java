package Geocoding;

import Geocoding.NER.NERTag;
import Geocoding.PosTags.PosTag;

import java.util.ArrayList;

/**
 * Created by alistair on 29/03/2017.
 */
public class Token {

    public String word;

    public boolean addressSegment;

    boolean namedEntity;

    public LocationType locationType;

    public PosTag posTag;

    NERTag nerTag;

    public Token(String word, boolean namedEntity) {
        this.word = word;
        this.namedEntity = namedEntity;
    }

    static void checkAddress(ArrayList<Token> tokens, String address) {
        for (Token t: tokens) {
            String s = t.word;
            if (address.toLowerCase().contains(s.toLowerCase())) t.addressSegment = true;
            else t.addressSegment = false;
        }
    }

    static void printTokens(ArrayList<Token> tokens) {
        for (Token t: tokens) {
            System.out.print(t.word);
        }
        System.out.println();
    }

}
