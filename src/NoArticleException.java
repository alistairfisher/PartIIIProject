/**
 * Created by alistair on 04/02/2017.
 */
public class NoArticleException extends Exception {

    int rowNumber;

    NoArticleException(int rowNumber) {
        rowNumber = rowNumber;
    }

}
