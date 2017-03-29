package Geocoding.APIInterfaces;

import Geocoding.AddressComponent;
import Geocoding.Geopoint;

import java.util.List;

/**
 * Created by alistair on 05/02/2017.
 */
public class Result {

    List<AddressComponent> address_components;
    String icon;
    public String name;
    Geometry geometry;

    public Geopoint getGeopoint() {
        double lat = Double.parseDouble(geometry.location.lat);
        double lng = Double.parseDouble(geometry.location.lng);
        Geopoint geopoint = new Geopoint();
        geopoint.name = name;
        geopoint.setGeopoint(lng,lat);
        return geopoint;
    }

}
