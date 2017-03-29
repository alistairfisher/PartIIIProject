package Geocoding;

import Geocoding.APIInterfaces.APIQuotaException;
import Geocoding.Exceptions.NoNamedEntitiesException;
import Geocoding.Exceptions.NoSuggestedLocationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alistair on 12/02/2017.
 */
public class Testing {

    static Geopoint[] getLabelLocations(Example[] examples) {
        Geopoint[] labelLocations = new Geopoint[examples.length];
        for (int i=0;i<examples.length;i++) {
            try {
                labelLocations[i] = examples[i].labels.bestGeopoint;
            }
            catch (NullPointerException e) {
                System.out.printf("No geopoint for line %d\n",i);
            }
        }
        return labelLocations;
    }

    static Geopoint[] getProposedLocations(Example[] examples) throws Exception {
        List<Geopoint> accum = new ArrayList();
        for (int i=0;i<examples.length;i++) {
            try {
                System.out.printf("%d: ",i);
                accum.add(Classifier.findLocation(examples[i]));
            }
            catch (NoSuggestedLocationException e) {
                System.out.printf("No suggestions for entity %s in example %d\n",e.locationName,i);
                accum.add(new Geopoint());
            }
            catch (NoNamedEntitiesException e) {
                System.out.printf("No entities found in example %d\n",i);
                accum.add(new Geopoint());
            }
            catch (APIQuotaException e) {
                System.out.println("Hit API quota");

            }
        }
        return accum.toArray(new Geopoint[accum.size()]);
    }

    static Double accuracy(Example[] examples) throws Exception {
        Geopoint[] labelLocations = getLabelLocations(examples);
        Geopoint[] proposedLocations = getProposedLocations(examples);
        int correctCount = 0;
        for (int i = 0;i<proposedLocations.length;i++) {
            Geopoint gold = labelLocations[i];
            Geopoint proposed = proposedLocations[i];
            System.out.print("Gold: ");
            gold.print();
            System.out.print("Proposed: ");
            proposed.print();
            if (gold.equals(proposed)) {
                correctCount++;
            }
        }
        System.out.println(correctCount);
        return (((double) correctCount)/examples.length);
    }

}
