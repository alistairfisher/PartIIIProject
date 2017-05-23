package Geocoding.SVM;

import Geocoding.Main.Example;
import Geocoding.Main.Token;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_problem;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alistair on 11/05/2017.
 */
public class SVMBaseline {

    //reads in the examples, creates a training instance for each token, trains SVM, applies

    //will use MetaCost as an extension

    public static double test(ArrayList<Example> training, ArrayList<Example> test) throws IOException {
        svm_model model = train(training);
        System.out.println("Finished training");
        svm_problem formatted_test = SVMExample.formatExamples(test);
        double correct = 0;
        double primary = 0;
        double primaryCorrect = 0;
        int total = formatted_test.y.length;
        for (int i = 0;i<total;i++) {
            double correctClass = formatted_test.y[i];
            double[] probs = new double[2];
            double proposedClass = svm.svm_predict_probability(model,formatted_test.x[i],probs);
            System.out.printf("%f,%f\n",probs[0],probs[1]);
            if (proposedClass == 1) {
                System.out.println("hm");
            }
            if (correctClass == 1) {
                primary++;
            }
            if (proposedClass == formatted_test.y[i]) {
                correct++;
                if (correctClass == 1) {
                    primaryCorrect++;
                }
            }
            if (i % 50 == 0) {
                System.out.printf("Tested %d examples\n",i);
            }
        }
        System.out.println(primaryCorrect/primary);
        System.out.println(primary);
        return (correct/total);
    }

    private static svm_model train(ArrayList<Example> data) {
        svm_problem problem = SVMExample.formatExamples(data);
        System.out.printf("Finished formatting examples, training set contains %d items\n",problem.y.length);
        svm.svm_check_parameter(problem,Parameters.getParameters());
        System.out.println("Parameters checked");
        return svm.svm_train(problem,Parameters.getParameters());
    }

    //first run: 0.9013068524397242
    //0.0 on primary class

}
