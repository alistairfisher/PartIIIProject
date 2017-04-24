package Geocoding.APIInterfaces;

/**
 * Created by alistair on 04/02/2017.
 *
 * Returned in calls to the Autocomplete API. Contains a place name, its unique ID and a list of properties.
 */
public class Prediction {

    String description;
    public String place_id;
    String[] types;

    public Prediction(String description, String place_id, String[] types) {
        this.description = description;
        this.place_id = place_id;
        this.types = types;
    }

    public void print() {
        System.out.printf("%s, %s, %s\n",description,place_id,types);
    }
}
