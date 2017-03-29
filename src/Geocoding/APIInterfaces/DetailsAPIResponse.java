package Geocoding.APIInterfaces;

/**
 * Created by alistair on 05/02/2017.
 */
public class DetailsAPIResponse {

    public Result result;

    public String status;

    void print() {
        System.out.println(status);
    }

}
