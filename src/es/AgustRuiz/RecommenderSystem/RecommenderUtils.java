/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static es.AgustRuiz.RecommenderSystem.Main.itemsHandler;
import static es.AgustRuiz.RecommenderSystem.Main.ratingsHandler;
import static es.AgustRuiz.RecommenderSystem.Main.neighborhoodHandler;
import static es.AgustRuiz.RecommenderSystem.Main.ratingsTrainingHandler;
import static es.AgustRuiz.RecommenderSystem.Main.trainingNeighborhoodHandler;
import static java.lang.Math.abs;
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
    public static Map<Integer, Double> RecommendationsKNN(int activeIdUser, int kSize) {
        Map<Integer, Double> neighborhood = neighborhoodHandler.calculateKNN(activeIdUser, kSize, true);
        Map<Integer, Double> recommendations = new TreeMap();
        Double avgActiveUser = ratingsHandler.GetAvgRatingsUser(activeIdUser);
        Integer currentIdItem;
        for (Item item : itemsHandler.getSet()) {
            currentIdItem = item.getIditem();
            if (ratingsHandler.Get(activeIdUser, currentIdItem) == null) {
                Double predictedRating = Prediction(neighborhood, activeIdUser, currentIdItem, ratingsHandler);
                if (predictedRating != null) {
                    recommendations.put(currentIdItem, predictedRating);
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
        Double prediction = null;

        Map<Integer, Double> neighborhood = trainingNeighborhoodHandler.calculateKNN(iduser, kSize, false);

        prediction = ratingsTrainingHandler.Get(iduser, iditem);
        if (prediction == null) {
            prediction = Prediction(neighborhood, iduser, iditem, ratingsTrainingHandler);
        }

        return prediction;
    }

    private static Double Prediction(Map<Integer, Double> neighborhood, int iduser, int iditem, GenericRatingHandler genericRatingHandler) {
        Integer currentIdUser;
        Double similarityNormalized, currentRating, prediction = null;
        double dividend = 0.0, divisor = 0.0;

        Double avgCurrentItem = genericRatingHandler.GetAvgRatingsItem(iditem);
        //Double avgActiveUser = genericRatingHandler.GetAvgRatingsUser(iduser);

        for (Entry<Integer, Double> neighbor : neighborhood.entrySet()) {
            currentIdUser = neighbor.getKey();
            similarityNormalized = NormalizeSimilarity(neighbor.getValue());
            currentRating = genericRatingHandler.Get(currentIdUser, iditem);
            if (currentRating != null) {
                dividend += similarityNormalized * (currentRating - avgCurrentItem);
                //dividend += similarityNormalized * (currentRating - avgCurrentUser);
                divisor += abs(similarityNormalized);
            }
        }
        if (divisor != 0) {
            prediction = avgCurrentItem + (dividend / divisor);
            //prediction = avgActiveUser + (dividend / divisor);
            if (prediction > 5) {
                prediction = 5.0;
            }
        }
        return prediction;
    }

    private static Double NormalizeSimilarity(double pearsonSimilarity) {
        return (pearsonSimilarity + 1) / 2;
    }
}
