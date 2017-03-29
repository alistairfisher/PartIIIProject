package Geocoding.APIInterfaces;

/**
 * Created by alistair on 04/02/2017.
 */
public class DetailsAPInterface extends APIInterface {

    private static final String urlPref =
            "https://maps.googleapis.com/maps/api/place/details/json?key="+APIKey+"&types=(cities)&placeid=";

    private static DetailsAPIResponse placeFromJSON(String json){
        return responseFromJSON(json, DetailsAPIResponse.class);
    }

    public static DetailsAPIResponse response(String place_id) throws Exception {
        String json = APIInterface.makeRequest(place_id,urlPref);
        return placeFromJSON(json);
    }

}
