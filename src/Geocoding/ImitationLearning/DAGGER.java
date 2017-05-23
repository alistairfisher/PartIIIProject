package Geocoding.ImitationLearning;

import Geocoding.Main.Example;
import Geocoding.Main.Token;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Created by alistair on 30/03/2017.
 */
public class DAGGER {

    public static Policy learn(ArrayList<Example> examples, double learningRate, int N) throws Exception {
        ArrayList<CSCExample> cscExamples = new ArrayList<>();
        LearnedPolicy lp = new LearnedPolicy();
        for (int i = 0; i < N; i++) {
            System.out.println("Iteration "+ i);
            double p = Math.pow((1-learningRate),i);
            int count = 0;
            for (Example e: examples) {
                if (count % 100 == 0) System.out.println("Producing examples for article "+count);
                ArrayList<Token> tokens = e.articles.get(0);
                ExpertPolicy ep = new ExpertPolicy(tokens);
                CompositePolicy cp = new CompositePolicy(lp, ep ,p);
                ArrayList<Boolean> prediction = cp.predict(tokens);
                for (int j = 0; j < prediction.size()+1; j++) {
                    ArrayList<Boolean> subActions =  new ArrayList<>(prediction.subList(0,j));
                    int[] costs = new int[2];
                    costs[0] = Cost.hammingCost(tokens,ep.completePrediction(tokens,subActions,true));
                    costs[1] = Cost.hammingCost(tokens,ep.completePrediction(tokens,subActions,false));
                    CSCExample example = new CSCExample(j, subActions, tokens,costs);
                    cscExamples.add(example);
                }
                count++;
            }
            //lp.classifier.train(cscExamples);
        }
        return lp;
    }

}
