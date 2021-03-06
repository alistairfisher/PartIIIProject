package Geocoding.NER;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * Created by alistair on 27/01/2017.
 */
public class NamedEntity {

    public final String name;
    final NERTag tag;

    private NamedEntity(String name, String tag) {
        this.name = name;
        this.tag = NERTag.valueOf(tag);
    }

    static NamedEntity formNamedEntity(String loc,CoreLabel label) {
        return new NamedEntity(loc,label.get(CoreAnnotations.AnswerAnnotation.class));
    }

}
