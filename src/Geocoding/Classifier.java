package Geocoding;

import Geocoding.APIInterfaces.*;
import Geocoding.Exceptions.NoNamedEntitiesException;
import Geocoding.Exceptions.NoSuggestedLocationException;
import Geocoding.NER.NER;
import Geocoding.NER.NamedEntity;

import java.util.ArrayList;

/**
 * Created by alistair on 12/02/2017.
 */
public class Classifier {

    public static Geopoint findLocation(Example e) throws Exception {
        String firstArticle = e.articles.get(0);
        System.out.println(firstArticle);
        ArrayList<NamedEntity> entities = NER.getLocations(firstArticle);
        if (entities.size()>0) {
            NamedEntity firstLocation = NER.getLocations(firstArticle).get(0);
            System.out.println(firstLocation.name);
            String locationName = firstLocation.name;
            AutocompleteAPIResponse autocompleteAPIResponse = AutocompleteAPIInterface.response(stringForURL(locationName));
            autocompleteAPIResponse.print();
            if (autocompleteAPIResponse.predictions.size() > 0) {
                String placeID = autocompleteAPIResponse.predictions.get(0).place_id;
                DetailsAPIResponse detailsAPIResponse = DetailsAPInterface.response(placeID);
                if(detailsAPIResponse.status.equals("OVER_QUERY_LIMIT")) {
                    throw new APIQuotaException();
                }
                System.out.println(detailsAPIResponse.result.name);
                return detailsAPIResponse.result.getGeopoint();
            }
            else if (autocompleteAPIResponse.status.equals("OVER_QUERY_LIMIT")) {
                throw new APIQuotaException();
            }
            else {
                throw new NoSuggestedLocationException(locationName);
            }
        }
        else {
            throw new NoNamedEntitiesException();
        }
    }

    static String stringForURL (String input) {
        return input.replace(" ","%20");
    }

}
