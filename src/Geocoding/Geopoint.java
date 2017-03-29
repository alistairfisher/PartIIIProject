package Geocoding;

/**
 * Created by alistair on 05/02/2017.
 */
public class Geopoint {

    double longitude;
    double latitude;
    public String name = "default";

    public Geopoint() {
    }

    private boolean toleranceEquals(double a, double b) {
        return (Math.abs(a-b)<1e-4);
    }

    public void setGeopoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
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
