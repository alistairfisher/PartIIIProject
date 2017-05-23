package Geocoding.SVM;

import Geocoding.Main.Example;
import libsvm.svm_node;
import libsvm.svm_problem;

import java.util.ArrayList;

/**
 * Created by alistair on 30/03/2017.
 */
public class SVMExample {

    boolean label;
    double[] features;
    SVMFeatures explicitFeatures;

    //produce a double[] from the explicit features
    double[] vectoriseFeatures() {
        double[] result = new double[2];
        //position is first feature
        result[0] = explicitFeatures.position;
        //words are the next three;
        //TODO
        //posTags next
        /*try {
            result[4] = explicitFeatures.precedingPosTag.ordinal();
        }
        catch (NullPointerException e) {
            result[4] = 0;
        }
        try {
            result[5] = explicitFeatures.posTag.ordinal();
        }
        catch (NullPointerException e) {
            result[5] = 0;
        }
        try {
            result[6] = explicitFeatures.followingPosTag.ordinal();
        }
        catch (NullPointerException e) {
            result[6] = 0;
        }
        //then named entity*/
        if (explicitFeatures.namedEntity) result[1] = 1;
        else result[1] = 0;
        return result;
    }

    private SVMExample(Example example, int tokenPosition) {
        this.label = example.articles.get(0).get(tokenPosition).addressSegment;
        this.explicitFeatures = new SVMFeatures(example, tokenPosition);
        this.features = vectoriseFeatures();
    }

    public svm_node[] convertToClassingExample() {
        ArrayList<svm_node> featureList = new ArrayList();
        for (int j = 0; j < this.features.length;j++) {
            if (!(this.features[j] == 0)) {
                svm_node node = new svm_node();
                node.index = j;
                node.value = this.features[j];
                featureList.add(node);
            }
        }
        return featureList.toArray(new svm_node[featureList.size()]);
    }

    //converts a single example to an svm problem
    private static svm_problem convertToSVMProblem(ArrayList<SVMExample> examples){
        svm_problem result = new svm_problem();
        int size = examples.size();
        result.l = size;
        result.y = new double[size];
        result.x = new svm_node[size][];
        for (int i = 0; i < size; i++) {
            SVMExample example = examples.get(i);
            if (example.label) {
                result.y[i] = 1;
            }
            else {
                result.y[i] = 2;
            }
            result.x[i] = example.convertToClassingExample();
        }
        return result;
    }

    public static svm_problem formatExamples(ArrayList<Example> examples) {
        ArrayList<SVMExample> svmExamples = new ArrayList<>();
        for (Example e: examples) {
            for (int i = 0; i < e.articles.get(0).size();i++) {
                svmExamples.add(new SVMExample(e,i));
            }
        }
        return convertToSVMProblem(svmExamples);
    }

}
