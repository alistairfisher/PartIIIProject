package Geocoding.APIInterfaces;

/**
 * Created by alistair on 27/01/2017.
 */
public class AutocompleteAPIInterface extends APIInterface {

    //Geocoding.Location[] lookuplocation (String name);

    private static final String urlPref =
            "https://maps.googleapis.com/maps/api/place/autocomplete/json?key="+APIKey+"&input=";

    private static AutocompleteAPIResponse placeFromJSON(String json){
        return responseFromJSON(json, AutocompleteAPIResponse.class);
    }

    public static AutocompleteAPIResponse response(String input) throws Exception {
        String json = APIInterface.makeRequest(input,urlPref);
        return placeFromJSON(json);
    }

}
