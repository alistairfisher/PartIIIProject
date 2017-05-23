package Geocoding.Main;

import Geocoding.APIInterfaces.StringChecker;
import Geocoding.ImitationLearning.DAGGER;
import Geocoding.NER.NER;
import Geocoding.NaiveBayes.StreetClassifier;
import Geocoding.NaiveBayes.StringMatchClassifier;
import Geocoding.NaiveBayes.TownClassifier;

import java.util.ArrayList;

/**
 * Created by alistair on 27/01/2017.
 */
class Main {

    public static void main(String[] args) throws Exception {
        NER.setclassifier();
        ArrayList<Example> examples = spreadsheetParser.parseFile();
        ArrayList<Example> trainingSet = new ArrayList<>(examples.subList(0,1767));
        ArrayList<Example> testSet = new ArrayList<>(examples.subList(1767,1967));
        ArrayList<Example> hidden = new ArrayList<>(examples.subList(1967, examples.size()));
        DAGGER.learn(trainingSet, 0.2, 100);
        /*StreetClassifier sc = new StreetClassifier();
        sc.trainClassifier(trainingSet);
        TownClassifier tc = new TownClassifier();
        tc.trainClassifier(trainingSet);
        StringMatchClassifier smc = new StringMatchClassifier();
        int ok = 0;
        for (Example example: hidden) {
            ArrayList<Token> tokens = example.articles.get(0);
            ArrayList<String> streetCandidates = sc.classifyExample(tokens, true);
            ArrayList<String> townCandidates = tc.classifyExample(tokens,false);
            ArrayList<String> stateCandidates = smc.classifyState(tokens);
            ArrayList<String> countryCandidates = smc.classifyCountry(tokens);
            AddressComposer ac = new AddressComposer(streetCandidates,townCandidates,stateCandidates,countryCandidates);
            String composedAddress = ac.propose()[0];
            Geopoint geopoint = StringChecker.checkString(composedAddress);
            example.labels.bestGeopoint.print();
            geopoint.print();
            double distance = example.labels.bestGeopoint.distance(geopoint);
            System.out.println(distance);
            if (distance < 5) {
                ok++;
                System.out.println(ok);
            }
        }
        System.out.println(ok);
        System.out.println(hidden.size());
        //LexicalFeatureSelection.parseExamples(trainingSet);
        //Double accuracy = Geocoding.Testing.accuracy(testSet.toArray(new Geocoding.Example[200]));
        //System.out.println(accuracy);*/
    }

}
