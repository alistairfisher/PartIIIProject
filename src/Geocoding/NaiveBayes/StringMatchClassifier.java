package Geocoding.NaiveBayes;

import Geocoding.Main.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by alistair on 18/05/2017.
 */
public class StringMatchClassifier {

    private String[] States = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
            "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
            "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
            "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
            "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
            "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
            "Wisconsin", "Wyoming"};

    private HashSet<String> USStates;

    private HashSet<String> Countries;

    private boolean setUp;

    private void setUpMaps() {
        USStates = new HashSet<>();
        for (String s: States) {
            USStates.add(s.toLowerCase());
        }
        Countries = new HashSet<>();
        Countries.add("u.s.");
        Countries.add("united states");
        Countries.add("united kingdom");
        Countries.add("uk");
        Countries.add("u.k.");
    }

    private boolean classifyToken(Token t, HashSet<String> map) {
        return map.contains(t.word.toLowerCase());
    }

    private ArrayList<String> classifyTokens(ArrayList<Token> tokens, boolean isState) {
        if (!setUp) setUpMaps();
        ArrayList<String> result = new ArrayList<>();
        HashSet<String> h;
        if (isState) h = USStates;
        else h = Countries;
        for (Token t: tokens) {
            if (classifyToken(t,h)) result.add(t.word);
        }
        return result;
    }

    public ArrayList<String> classifyState(ArrayList<Token> tokens) {
        if (!setUp) setUpMaps();
        return classifyTokens(tokens,true);
    }

    public ArrayList<String> classifyCountry(ArrayList<Token> tokens) {
        if (!setUp) setUpMaps();
        return classifyTokens(tokens,false);
    }

}
