/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static es.AgustRuiz.RecommenderSystem.Main.itemsHandler;
import static es.AgustRuiz.RecommenderSystem.Main.ratingsHandler;
import static es.AgustRuiz.RecommenderSystem.Main.neighborhoodHandler;
import static es.AgustRuiz.RecommenderSystem.Main.usersHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Practice02 {

    /// K values
    private static List<Integer> kSizes = new ArrayList();

    /**
     * Gets all k values in string format
     * @return All k values in string format
     */
    public static String getKSizes() {
        if (kSizes.size() == 0) {
            return "none";
        } else {
            StringBuilder output = new StringBuilder();
            for (Integer s : kSizes) {
                output.append(s.toString()).append(", ");
            }
            output.deleteCharAt(output.length() - 1);
            output.deleteCharAt(output.length() - 1);
            output.append(" (total: " + kSizes.size() + ")");
            return output.toString();
        }
    }

    /**
     * Clears all k values
     */
    public static void clearKSizes() {
        kSizes.clear();
    }

    /**
     * Adds a new k value (if k>0)
     *
     * @param newK New k value
     */
    public static void addKSize(int newK) {
        if (newK > 0) {
            kSizes.add(newK);
        }
    }
    
    /**
     * Checks if exists a K value in the K sizes list
     * @param k K value to check
     * @return True if is already registered of false otherwise
     */
    public static Boolean hasKSize(int k){
        return kSizes.contains(k);
    }

}
