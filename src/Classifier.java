import java.util.ArrayList;

/**
 * Created by alistair on 12/02/2017.
 */
public class Classifier {

    public static Geopoint findLocation(Example e) throws NoSuggestedLocationException, Exception {
        String firstArticle = e.articles.get(0);
        System.out.println(firstArticle);
        ArrayList<NamedEntity> entities = NER.getLocations(firstArticle);
        if (entities.size()>0) {
            NamedEntity firstLocation = NER.getLocations(firstArticle).get(0);
            System.out.println(firstLocation.name);
            String locationName = firstLocation.name;
            AutocompleteAPIResponse autocompleteAPIResponse = AutocompleteAPIInterface.response(stringForURL(locationName));
            if (autocompleteAPIResponse.predictions.size() > 0) {
                String placeID = autocompleteAPIResponse.predictions.get(0).place_id;
                DetailsAPIResponse detailsAPIResponse = DetailsAPInterface.response(placeID);
                System.out.println(detailsAPIResponse.result.name);
                return detailsAPIResponse.result.getGeopoint();
            } else {
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
