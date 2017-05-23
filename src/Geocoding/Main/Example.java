package Geocoding.Main;

import Geocoding.NER.NamedEntity;

import java.util.ArrayList;

/**
 * Created by alistair on 03/02/2017.
 */
public class Example {

    public String id;

    public String title;
    public ArrayList<ArrayList<Token>> articles;
    public ArrayList<NamedEntity> entities;

    public final Labels labels = new Labels();

}
