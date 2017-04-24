package Geocoding.APIInterfaces;

/**
 * Created by alistair on 05/02/2017.
 *
 * The class returned by the DetailsAPIInterface. Contains a status code and a Result object, which contains a place
 * name and a set of GPS co-ordinates.
 */
public class DetailsAPIResponse {

    public Result result;

    public String status;

}
