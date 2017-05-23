package Geocoding.APIInterfaces;

import Geocoding.Exceptions.NoSuggestedLocationException;
import Geocoding.Main.Geopoint;

/**
 * Created by alistair on 18/05/2017.
 */
public class StringChecker {

    private static String stringForURL(String s) {
        String noPunc = s.replaceAll("[^A-Za-z0-9 ]", "");
        return noPunc.replace(" ","%20");
    }

    public static Geopoint checkString(String s) throws Exception {
        System.out.println(s);
        AutocompleteAPIResponse autocompleteAPIResponse = AutocompleteAPIInterface.response(stringForURL(s));
        if (autocompleteAPIResponse.predictions.size() > 0) {
            String placeID = autocompleteAPIResponse.predictions.get(0).place_id;
            DetailsAPIResponse detailsAPIResponse = DetailsAPInterface.response(placeID);
            if(detailsAPIResponse.status.equals("OVER_QUERY_LIMIT")) {
                throw new APIQuotaException();
            }
            return detailsAPIResponse.result.getGeopoint();
        }
        else if (autocompleteAPIResponse.status.equals("OVER_QUERY_LIMIT")) {
            throw new APIQuotaException();
        }
        else {
            Geopoint result = new Geopoint();
            result.name = "No suggestion";
            return result;
        }
    }

}
