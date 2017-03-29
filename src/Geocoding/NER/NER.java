package Geocoding.NER; /**
 * Created by alistair on 27/01/2017.
 */

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.ArrayList;
import java.util.List;


public class NER {

    private static AbstractSequenceClassifier<CoreLabel> getClassifier() throws Exception {
        return CRFClassifier.getClassifier("/Users/alistair/Documents/DissResources/stanford-ner-2016-10-31/classifiers/english.all.3class.distsim.crf.ser.gz");
    }

    private static ArrayList<NamedEntity> classify(String s) throws Exception {
        List<List<CoreLabel>> out = getClassifier().classify(s);
        ArrayList result = new ArrayList<NamedEntity>();
        for (List<CoreLabel> sentence : out) {
            CoreLabel previous = null;
            String loc = null;
            for (CoreLabel word : sentence) {
                String clas = word.get(CoreAnnotations.AnswerAnnotation.class);
                if (!(clas.equals("O"))) {
                    if (previous!= null) {
                        if (word.get(CoreAnnotations.AnswerAnnotation.class).equals(previous.get(CoreAnnotations.AnswerAnnotation.class))) {
                            loc += (' ' + word.word());
                        }
                        else {
                            result.add(NamedEntity.formNamedEntity(loc,previous));
                            loc = word.word();
                        }
                    }
                    else {
                        loc = word.word();
                    }
                    previous = word;
                }
                else {
                    if (previous != null) {
                        result.add(NamedEntity.formNamedEntity(loc,previous));
                    }
                    previous = null;
                    loc = null;
                }
            }
        }
        return result;
    }

    public static ArrayList<NamedEntity> getLocations(String s) throws Exception {
        ArrayList<NamedEntity> entities = classify(s);
        ArrayList<NamedEntity> result = new ArrayList<>();
        for (NamedEntity e: entities) {
            if (e.tag.equals(NERTag.LOCATION)) {
                result.add(e);
            }
        }
        return result;
    }

    private static void print(String loc, CoreLabel label) {
        System.out.print(loc + '/' + label.get(CoreAnnotations.AnswerAnnotation.class) + '\n');
    }

}
