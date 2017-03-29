package Geocoding.Exceptions;

/**
 * Created by alistair on 04/02/2017.
 */
public class NoArticleException extends Exception {

    public int rowNumber;

    public NoArticleException(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
