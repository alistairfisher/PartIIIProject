package Geocoding;

/**
 * Created by alistair on 04/02/2017.
 */
public class Prediction {

    String description;
    String place_id;
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
