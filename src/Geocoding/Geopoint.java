package Geocoding;

/**
 * Created by alistair on 05/02/2017.
 */
public class Geopoint {

    double longitude;
    double latitude;
    public String name = "default";

    boolean houseNumber;

    public String streetName;
    public String townName;
    public String stateName;
    public String country;

    public Geopoint() {
    }

    private boolean toleranceEquals(double a, double b) {
        return (Math.abs(a-b)<1e-4);
    }

    public void setGeopoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //todo: account for multiple word names
    public void setAddressComponents() {
        String[] addressParts = name.split(", ");
        if (houseNumber) {
            streetName = addressParts[0].split(" ")[1];
        }
        else streetName = addressParts[0];
        if (country.equals("United Kingdom")) {
            townName = addressParts[1].split(" ")[0];
        }
        else {
            townName = addressParts[1];
            stateName = addressParts[2].split(" ")[0];
        }
    }

    void print() {
        System.out.printf("Name: %s, Long: %f, Lat: %f\n",name,longitude,latitude);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Geopoint) {
            Geopoint g = (Geopoint) o;
            return ((toleranceEquals(this.latitude,g.latitude)));// && (toleranceEquals(this.longitude,g.longitude)));
        }
        else return false;
    }
}
