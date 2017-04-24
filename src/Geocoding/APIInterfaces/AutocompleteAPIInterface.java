package Geocoding.APIInterfaces;

/**
 * Created by alistair on 27/01/2017.
 *
 * The class used to interact with the autocomplete API. A wrapper for APIInterface, inserting the correct URL and
 * response type. The API uses a string to return a set of place ids, with associated locations.
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
