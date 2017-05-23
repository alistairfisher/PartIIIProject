package Geocoding.SVM;

import libsvm.svm_parameter;

import static libsvm.svm_parameter.C_SVC;
import static libsvm.svm_parameter.LINEAR;

/**
 * Created by alistair on 25/04/2017.
 */
public class Parameters {

    private static svm_parameter parameters;

    private static boolean instantiated = false;

    static svm_parameter getParameters() {
        if (instantiated) return parameters;
        else {
            instantiateParameters();
            instantiated = true;
            return parameters;
        }
    }

    private static void instantiateParameters() {
        parameters = new svm_parameter();
        parameters.svm_type = C_SVC;
        parameters.kernel_type = LINEAR;
        parameters.C = 1;
        parameters.probability = 1;
    }

}
