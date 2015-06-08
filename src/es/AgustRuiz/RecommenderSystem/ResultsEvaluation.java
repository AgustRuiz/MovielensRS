/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class ResultsEvaluation {

    /// Map for evaluations. Map<MAE, EvaluationItem>
    private Map<Double, EvaluationItem> listEvaluations;

    /**
     * Constructor
     */
    public ResultsEvaluation() {
        this.listEvaluations = new TreeMap();
    }
    
    /**
     * Add new evaluation result
     * @param kValue K value (of KNN)
     * @param time Time (in ms)
     * @param mae Mean Absolute Error
     */
    public void addEvaluation(int kValue, long time, double mae){
        this.listEvaluations.put(mae, new EvaluationItem(kValue, time, mae));
    }
    
    /**
     * Get results in String format
     * @return Results in string format
     */
    public String getResults(){
        String output = "";
        for(EvaluationItem evaluation : this.listEvaluations.values()){
            output += "MAE = " + evaluation.mae + "\t| K = " + evaluation.k + "\t| Time:  " + evaluation.time + "ms\n";
        }
        return output;
    }

    /**
     * Class for Evalutator Item
     */
    class EvaluationItem {

        public int k;
        public long time;
        public double mae;

        public EvaluationItem(int k, long time, double mae) {
            this.k = k;
            this.time = time;
            this.mae = mae;
        }
    }

}
