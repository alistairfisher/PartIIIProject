/**
 * Created by alistair on 27/01/2017.
 */

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;

import java.util.HashSet;
import java.util.List;

import java.io.IOException;
import java.util.Set;


public class NER {

    final static AbstractSequenceClassifier<CoreLabel> getClassifier() throws Exception {
        return CRFClassifier.getClassifier("/Users/alistair/Documents/DissResources/stanford-ner-2016-10-31/classifiers/english.all.3class.distsim.crf.ser.gz");
    }

    static Set<NamedEntity> classify(String s) throws Exception {
        List<List<CoreLabel>> out = getClassifier().classify(s);
        Set result = new HashSet<NamedEntity>();
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
                            print(loc,previous);
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
                        print(loc,previous);
                    }
                    previous = null;
                    loc = null;
                }
            }
            System.out.println();
        }
        return result;
    }

    static void print(String loc, CoreLabel label) {
        System.out.print(loc + '/' + label.get(CoreAnnotations.AnswerAnnotation.class) + '\n');
    }

    public NER() throws IOException, ClassNotFoundException {
    }
}
