package Geocoding.APIInterfaces;

import java.util.List;

/**
 * Created by alistair on 04/02/2017.
 *
 * The class used to represent responses from the Autocomplete API. It returns a status code and a list of Prediction
 * objects, which encode places.
 */
public class AutocompleteAPIResponse {

    public String status;
    public List<Prediction> predictions;

    public void print() {
        System.out.println(status);
        for (Prediction p: predictions) {
            p.print();
        }
    }

}
