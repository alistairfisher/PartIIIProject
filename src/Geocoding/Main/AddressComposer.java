package Geocoding.Main;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by alistair on 18/05/2017.
 */
public class AddressComposer {

    public AddressComposer(ArrayList<String> streets, ArrayList<String> towns, ArrayList<String> states, ArrayList<String> countries) {
        this.streets = streets;
        this.towns = towns;
        this.states = states;
        this.countries = countries;
        String[] stNames = {"ave.", "avenue", "dr.", "drive", "ln.", "lane", "rd.", "rd", "road", "st.", "street"};
        for (String s: stNames) {
            this.streetNames.add(s);
        }
    }

    ArrayList<String> streets;
    ArrayList<String> towns;
    ArrayList<String> states;
    ArrayList<String> countries;
    HashSet<String> streetNames = new HashSet<>();

    private String end() {
        if (states.size() > 0) return states.get(0);
        else if (countries.size()>0) return countries.get(0);
        else return "";
    }

    private String first() {
        if (streets.size() > 0) {
            String result = streets.get(0);
            if (streets.size() > 1) {
                if (streetNames.contains(streets.get(1).toLowerCase())) result = result + " " + streets.get(1);
            }
            for (String s: towns) {
                if (!result.contains(s)) {
                    result+=(" "+s);
                    break;
                }
            }
            return result;
        }
        else if (towns.size() > 0) return towns.get(0);
        else return "";
    }

    String[] propose() {
        String[] result = new String[1];
        result[0] = first();
        if (end().length() > 0) {
            result[0] += (" "+end());
        }
        return result;
    }

}
