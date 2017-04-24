package Geocoding.Exceptions;

/**
 * Created by alistair on 04/02/2017.
 *
 * Thrown during parsing the spreadsheet, at rows where there's no articles.
 */
public class NoArticleException extends Exception {

    public int rowNumber;

    public NoArticleException(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
