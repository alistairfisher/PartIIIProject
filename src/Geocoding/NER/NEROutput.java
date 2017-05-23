package Geocoding.NER;

import Geocoding.Main.Token;

import java.util.ArrayList;

/**
 * Created by alistair on 30/03/2017.
 */
public class NEROutput {

    public final ArrayList<Token> tokens;

    public final ArrayList<NamedEntity> entities;

    public NEROutput(ArrayList<Token> tokens, ArrayList<NamedEntity> entities) {
        this.tokens = tokens;
        this.entities = entities;
    }

}
