/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static es.AgustRuiz.RecommenderSystem.Main.usersHandler;
import static es.AgustRuiz.RecommenderSystem.Main.itemsHandler;
import static es.AgustRuiz.RecommenderSystem.Main.ratingsHandler;
import static es.AgustRuiz.RecommenderSystem.Main.neighborhoodHandler;
import static es.AgustRuiz.RecommenderSystem.Main.ratingsTrainingHandler;
import static es.AgustRuiz.RecommenderSystem.Main.trainingNeighborhoodHandler;
import static java.lang.Math.abs;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RecommenderUtils {

    /**
     * @param activeIdUser Active user id
     * @param activeIdUser Neighborhood size
     * @return Recommendations in Map<Rating, IdItem> format
     */
    public static Map<Double, Integer> RecommendationsKNN(int activeIdUser, int kSize) {
        Map<Double, Integer> neighborhood = neighborhoodHandler.calculateKNN(activeIdUser, kSize, true);
        Map<Double, Integer> recommendations = new TreeMap(Collections.reverseOrder());
        Double avgActiveUser = ratingsHandler.GetAvgRatingsUser(activeIdUser);
        Integer currentIdItem, currentIdUser;
        for (Item item : itemsHandler.getSet()) {
            currentIdItem = item.getIditem();
            Double dividend = 0.0, divisor = 0.0, similarity = 0.0, currentRating;
            if (ratingsHandler.Get(activeIdUser, currentIdItem) == null) {
                for (Map.Entry<Double, Integer> neighbor : neighborhood.entrySet()) {
                    similarity = neighbor.getKey();
                    currentIdUser = neighbor.getValue();
                    currentRating = ratingsHandler.Get(currentIdUser, currentIdItem);
                    if (currentRating != null) {
                        dividend += similarity * (currentRating - ratingsHandler.GetAvgRatingsUser(currentIdUser));
                        divisor += abs(similarity);
                    }
                }
                if (divisor != 0) {
                    double predictedRating = avgActiveUser + (dividend / divisor);
                    if (predictedRating > 5) {
                        predictedRating = 5.0;
                    }
                    recommendations.put(predictedRating, currentIdItem);
                }
            }
        }
        return recommendations;
    }

    /**
     * Make a prediction from training dataset
     *
     * @param iduser Active user id
     * @param iditem Item id
     * @param kSize Neighborhood size
     * @return
     */
    public static Double PredictRating_Training(int iduser, int iditem, int kSize) {
        
        if(iduser == 311 && iditem == 28){
            System.out.println("QUIETORL!");
        }
        
        Double prediction = null;
        
        int currentIdUser;
        Double similarity, currentRating, dividend, divisor;
        Double avgActiveUser = ratingsTrainingHandler.GetAvgRatingsUser(iduser);

        Map<Double, Integer> neighborhood = trainingNeighborhoodHandler.calculateKNN(iduser, kSize, false);

        dividend = new Double(0);
        divisor = new Double(0);

        if (ratingsTrainingHandler.Get(iduser, iditem) == null) {
            for (Entry<Double, Integer> neighbor : neighborhood.entrySet()) {
                similarity = neighbor.getKey();
                currentIdUser = neighbor.getValue();
                currentRating = ratingsTrainingHandler.Get(currentIdUser, iditem);
                if (currentRating != null) {
                    dividend += similarity * (currentRating - ratingsTrainingHandler.GetAvgRatingsUser(currentIdUser));
                    divisor += abs(similarity);
                }
                //break;
            }
            if (divisor != 0) {
                prediction = avgActiveUser + (dividend / divisor);
                if(prediction > 5){
                    prediction = 5.0;
                }
            }
        }else{
            prediction = ratingsTrainingHandler.Get(iduser, iditem);
        }

        return prediction;
    }

}
