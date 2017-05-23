package Geocoding.APIInterfaces;

import Geocoding.Main.AddressComponent;
import Geocoding.Main.Geopoint;

import java.util.List;

/**
 * Created by alistair on 05/02/2017.
 *
 * The main result from the Details API, contains a place name and its location.
 */
public class Result {

    List<AddressComponent> address_components;
    String icon;
    public String name;
    private Geometry geometry;

    public Geopoint getGeopoint() {
        double lat = Double.parseDouble(geometry.location.lat);
        double lng = Double.parseDouble(geometry.location.lng);
        Geopoint geopoint = new Geopoint();
        geopoint.name = name;
        geopoint.setGeopoint(lng,lat);
        return geopoint;
    }

}
