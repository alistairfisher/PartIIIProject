package Geocoding.Main;

/**
 * Created by alistair on 05/02/2017.
 */
public class Geopoint {

    private double longitude;
    private double latitude;
    public String name = "default";

    boolean houseNumber;

    public String country;


    private boolean toleranceEquals(double a, double b) {
        return (Math.abs(a-b)<1e-4);
    }

    public void setGeopoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double distance(Geopoint other) {

        final int R = 6371; // Radius of the earth

        double lat2 = other.latitude;
        double lon2 = other.longitude;

        //deals with case of erroneous gps
        if ((toleranceEquals(this.latitude,this.longitude)) || toleranceEquals(lat2,lon2)) {
            System.out.println("here");
            lon2 = this.longitude;
        }

        double latDistance = Math.toRadians(lat2 - this.latitude);
        double lonDistance = Math.toRadians(lon2 - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return (Math.sqrt(distance)/1000);
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
