/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Recommender and evaluator using Hold-Out
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Recommender_Evaluator {

    /// Active user id
    private static int activeUserId = 23;

    /// K values to evaluate
    private static List<Integer> kValues = new ArrayList();

    /**
     * Add a new k value to evaluate
     *
     * @param k K value
     */
    public static void addKValue(int k) {
        if (k > 0) {
            kValues.add(k);
        }
    }

    /**
     * Reset K valuese to evaluate
     */
    public static void resetKValue() {
        kValues.clear();
    }

    /**
     * Get K values
     *
     * @return Integer[] with all K values to evaluate
     */
    public static Integer[] getKValues() {
        return (Integer[]) kValues.toArray();
    }

    /**
     * Get k values in string format, ready for console using
     *
     * @return K values in string output format
     */
    public static String getKValuesInString() {
        if (kValues.size() == 0) {
            return "none";
        } else {
            StringBuilder output = new StringBuilder();
            for (Integer s : kValues) {
                output.append(s.toString()).append(", ");
            }
            output.deleteCharAt(output.length() - 1);
            output.deleteCharAt(output.length() - 1);
            output.append(" (total: " + kValues.size() + ")");
            return output.toString();
        }
    }

    /**
     * Get active user id
     * @return Active user id
     */
    public static int getActiveUserId() {
        return Recommender_Evaluator.activeUserId;
    }
    
    /**
     * Set active user id
     * @param iduser Active user id
     */
    public static void setActiveUserId(int iduser){
        Recommender_Evaluator.activeUserId = iduser;
    }

}
