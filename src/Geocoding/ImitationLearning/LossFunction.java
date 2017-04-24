package Geocoding.ImitationLearning;

import Geocoding.LocationType;
import Geocoding.Token;

/**
 * Created by alistair on 30/03/2017.
 */
public class LossFunction {

    static int loss(Prediction prediction) {
        int result = 0;
        for (int i = 0; i < prediction.tokens.size(); i++) {
            Token t = prediction.tokens.get(i);
            Boolean segment = prediction.actions.get(i);
            if (segment.equals(t.addressSegment)) {
                result++;
            }
        }
        return result;
    }

}
