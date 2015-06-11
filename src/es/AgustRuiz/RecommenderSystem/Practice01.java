/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Practice01 {

    private static int activeIduser = 23;
    private static int kSize = 20;

    /**
     * Gets active user id
     * @return Active user id
     */
    public static int getActiveIduser() {
        return activeIduser;
    }

    /**
     * Sets active user id
     * @param activeIduser 
     */
    public static void setActiveIduser(int activeIduser) {
        Practice01.activeIduser = activeIduser;
    }

    /**
     * Gets k size (of KNN)
     * @return k size (of KNN)
     */
    public static int getkSize() {
        return kSize;
    }

    /**
     * Sets k size (of KNN)
     * @param kSize
     */
    public static void setkSize(int kSize) {
        Practice01.kSize = kSize;
    }

    /**
     * Run recommender
     */
    public static void Run() {
        long time;

        // NEIGHBORHOOD
        System.out.print("Calculating neighborhood for user " + activeIduser + " (" + kSize + "-nn)... ");
        time = System.currentTimeMillis();
        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

        // RECOMMENDATIONS
        System.out.print("Calculating recommendations for user " + activeIduser + "... ");
        time = System.currentTimeMillis();

        Map<Double, Integer> recommendations = RecommenderUtils.RecommendationsKNN(activeIduser, kSize);

        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

        for (Entry<Double, Integer> entry : recommendations.entrySet()) {
            System.out.println("Item: " + entry.getValue() + "\t Rating: " + entry.getKey());
        }
    }

}
