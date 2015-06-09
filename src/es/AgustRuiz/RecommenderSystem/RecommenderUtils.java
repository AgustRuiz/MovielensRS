/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.abs;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RecommenderUtils {
    
    /**
     * Make recommendations for an user
     * @param users Handler of users
     * @param items Handler of items
     * @param ratings Handler of ratings
     * @param activeIdUser Active user id
     * @param neighborhood Active user neighborhood
     * @return Recommendations in Map<Rating, IdItem> format
     */
    public static Map<Double, Integer> MakeRecommendations(UserHandler users, ItemHandler items, RatingHandler ratings, int activeIdUser, Map<Double, Integer> neighborhood){
        Map<Double, Integer> recommendations = new TreeMap(Collections.reverseOrder());
        Double avgActiveUser = ratings.getAvgRatingsUser(activeIdUser);
        Integer currentIdItem, currentIdUser;
        for (Item item : items.getSet()) {
            currentIdItem = item.getIditem();
            Double dividend = 0.0, divisor = 0.0, similarity = 0.0, currentRating;
            if (ratings.get(activeIdUser, currentIdItem) == null) {
                for (Map.Entry<Double, Integer> neighbor : neighborhood.entrySet()) {
                    similarity = neighbor.getKey();
                    currentIdUser = neighbor.getValue();
                    currentRating = ratings.get(currentIdUser, currentIdItem);
                    if (currentRating != null) {
                        dividend += similarity * (currentRating - ratings.getAvgRatingsUser(currentIdUser));
                        divisor += abs(similarity);
                    }
                }
                if (divisor != 0) {
                    double predictedRating = avgActiveUser + (dividend / divisor);
                    recommendations.put(predictedRating, currentIdItem);
                }
            }
        }
        return recommendations;
    }
    
}
