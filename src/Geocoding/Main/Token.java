package Geocoding.Main;

import Geocoding.NER.NERTag;
import Geocoding.PosTags.PosTag;

import java.util.ArrayList;

/**
 * Created by alistair on 29/03/2017.
 */
public class Token {

    public final String word;

    public boolean addressSegment;

    public LocationType locationType;

    public int position;

    public final boolean namedEntity;

    public PosTag posTag;

    public NERTag nerTag;

    public Token(String word, boolean namedEntity) {
        if (word.toLowerCase().equals("st.")) {
            this.word = "Street";
        }
        else if (word.toLowerCase().equals("ave.")) {
            this.word = "Avenue";
        }
        else this.word = word;
        this.namedEntity = namedEntity;
    }

    public Token(String word, boolean namedEntity, NERTag nerTag) {
        if (word.toLowerCase().equals("st.")) {
            this.word = "Street";
        }
        else if (word.toLowerCase().equals("ave.")) {
            this.word = "Avenue";
        }
        else this.word = word;
        this.namedEntity = namedEntity;
        this.nerTag = nerTag;
    }

    private static boolean checkString(String s1, String s2) {
        if (s2 == null) return false;
        else {
            String[] split = s2.split(" ");
            for (String s : split) {
                if (s.toLowerCase().equals(s1.toLowerCase())) return true;
            }
            return false;
        }
    }

    static void checkAddress(ArrayList<Token> tokens, String address, Location location) {
        for (Token t: tokens) {
            String s = t.word;
            if (Character.isUpperCase(s.charAt(0))) {
                t.addressSegment = (address.toLowerCase().contains(s.toLowerCase()) && s.length() > 2 && !(s.equals("the")) && !(s.equals("and")) &&
                        !(s.equals("each")));
            }
            t.checkLocationType(location);
        }
    }

    void checkLocationType(Location location) {
        if (checkString(word, location.streetName)) this.locationType = LocationType.STREET;
        else if (checkString(word, location.townName)) this.locationType = LocationType.TOWN;
        else if (checkString(word, location.stateName)) this.locationType = LocationType.STATE;
        else if (checkString(word, location.countryName)) this.locationType = LocationType.COUNTRY;
        else this.locationType = LocationType.NONE;
    }

    static void checkPosition(ArrayList<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            tokens.get(i).position = i;
        }
    }

    static void printTokens(ArrayList<Token> tokens) {
        for (Token t: tokens) {
            System.out.print(t.word);
        }
        System.out.println();
    }

}
