package Geocoding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alistair on 27/01/2017.
 */
class Main {

    /*
    Structure:
      * Get input
      * Tokenize?
      * Pass into the Geocoding.NER.NER, receive named entities, place in a set
       * Take first, resolve from Google Places API
     */

    public static void main(String[] args) throws Exception {
        ArrayList<Example> examples = spreadsheetParser.parseFile();
        System.out.println(examples.size());
        List<Example> testSet = examples.subList(0,200);
        //Double accuracy = Geocoding.Testing.accuracy(testSet.toArray(new Geocoding.Example[200]));
        //System.out.println(accuracy);
    }

}
