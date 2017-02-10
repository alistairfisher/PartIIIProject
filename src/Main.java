import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by alistair on 27/01/2017.
 */
public class Main {

    /*
    Structure:
      * Get input
      * Tokenize?
      * Pass into the NER, receive named entities, place in a set
       * Take first, resolve from Google Places API
     */

    public static void main(String[] args) throws Exception {
        String text = "US President Donald Trump and British Prime Minister Theresa May have reaffirmed their commitment to the Nato alliance after White House talks.\n" +
                "Mrs May confirmed Mr Trump was \"100% in favour of Nato\" despite the president's recent comments calling the trans-Atlantic alliance obsolete.\n" +
                "Both leaders said they would work to strengthen commercial ties.\n" +
                "Mrs May also said Mr Trump had accepted an invitation from the Queen for a state visit later this year.";
        //NER.classify(text);
        //AutocompleteAPIResponse rep = AutocompleteAPIInterface.response("London");
        //String place_id = rep.predictions.get(0).place_id;
        //DetailsAPIResponse det = DetailsAPInterface.response(place_id);
        //System.out.println(det.result.address_components.get(2).types.get(0));
        ArrayList<Example> examples = spreadsheetParser.parseFile();
    }

}
