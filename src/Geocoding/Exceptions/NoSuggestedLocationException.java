package Geocoding.Exceptions;

/**
 * Created by alistair on 24/02/2017.
 */
public class NoSuggestedLocationException extends Exception {

    public String locationName;

    public NoSuggestedLocationException(String locationName) {
        this.locationName = locationName;
    }
}
