package Geocoding;

import Geocoding.NER.NamedEntity;

import java.util.ArrayList;

/**
 * Created by alistair on 03/02/2017.
 */
public class Example {

    String id;

    String title;
    ArrayList<ArrayList<Token>> articles;
    ArrayList<NamedEntity> entities;

    final Labels labels = new Labels();

}
