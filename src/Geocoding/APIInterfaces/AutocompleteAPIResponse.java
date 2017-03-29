package Geocoding.APIInterfaces;

import Geocoding.Prediction;

import java.util.List;

/**
 * Created by alistair on 04/02/2017.
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
