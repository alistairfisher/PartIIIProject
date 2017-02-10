/**
 * Created by alistair on 04/02/2017.
 */
class NoArticleException extends Exception {

    int rowNumber;

    public NoArticleException(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
