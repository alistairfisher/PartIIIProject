package Geocoding.NER;

import Geocoding.Token;

import java.util.ArrayList;

/**
 * Created by alistair on 30/03/2017.
 */
public class NEROutput {

    public ArrayList<Token> tokens;

    public ArrayList<NamedEntity> entities;

    public NEROutput(ArrayList<Token> tokens, ArrayList<NamedEntity> entities) {
        this.tokens = tokens;
        this.entities = entities;
    }

}
