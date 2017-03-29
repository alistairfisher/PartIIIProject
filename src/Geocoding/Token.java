package Geocoding;

/**
 * Created by alistair on 29/03/2017.
 */
public class Token {

    String word;

    boolean addressSegment;

    public Token(String word, boolean addressSegment) {
        this.word = word;
        this.addressSegment = addressSegment;
    }
}
