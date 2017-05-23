package Geocoding.NER; /**
 * Created by alistair on 27/01/2017.
 */

import Geocoding.PosTags.PosTag;
import Geocoding.PosTags.PosTagger;
import Geocoding.Main.Token;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NER {

    public static void setclassifier() {
        try {
            classifier =  CRFClassifier.getClassifier("/Users/alistair/Documents/DissResources/stanford-ner-2016-10-31/classifiers/english.all.3class.distsim.crf.ser.gz");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static AbstractSequenceClassifier<CoreLabel> classifier;

    private static NEROutput classify(String s) throws Exception {
        List<List<CoreLabel>> out = classifier.classify(s);
        ArrayList<Token> tokens = new ArrayList<>();
        ArrayList<NamedEntity> entities = new ArrayList<>();
        for (List<CoreLabel> sentence : out) {
            CoreLabel previous = null;
            String loc = null;
            for (CoreLabel word : sentence) {
                String clas = word.get(CoreAnnotations.AnswerAnnotation.class);
                if (!(clas.equals("O"))) {
                    tokens.add(new Token(word.originalText(),true,NERTag.valueOf(clas)));
                    if (previous!= null) {
                        if (word.get(CoreAnnotations.AnswerAnnotation.class).equals(previous.get(CoreAnnotations.AnswerAnnotation.class))) {
                            loc += (' ' + word.word());
                        }
                        else {
                            entities.add(NamedEntity.formNamedEntity(loc,previous));
                            loc = word.word();
                        }
                    }
                    else {
                        loc = word.word();
                    }
                    previous = word;
                }
                else {
                    tokens.add(new Token(word.originalText(),false));
                    if (previous != null) {
                        entities.add(NamedEntity.formNamedEntity(loc,previous));
                    }
                    previous = null;
                    loc = null;
                }
            }
        }
        for (Token t: tokens) {
            String[] split = ((PosTagger.tagger.tagString(t.word)).split("_"));
            try {
                t.posTag = PosTag.valueOf(split[split.length - 1].trim());
            }
            catch (IllegalArgumentException e) {
                t.posTag = PosTag.SYM;
            }
        }
        return new NEROutput(tokens,entities);
    }

    public static NEROutput getLocations(String s) throws Exception {
        NEROutput output = classify(s);
        ArrayList<NamedEntity> entities = output.entities;
        ArrayList<NamedEntity> result = new ArrayList<>();
        for (NamedEntity e: entities) {
            if (e.tag.equals(NERTag.LOCATION)) {
                result.add(e);
            }
        }
        return new NEROutput(output.tokens,result);
    }

    private static void print(String loc, CoreLabel label) {
        System.out.print(loc + '/' + label.get(CoreAnnotations.AnswerAnnotation.class) + '\n');
    }

}
