/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.util.Calendar;
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
     *
     * @return Active user id
     */
    public static int getActiveIduser() {
        return activeIduser;
    }

    /**
     * Sets active user id
     *
     * @param activeIduser
     */
    public static void setActiveIduser(int activeIduser) {
        Practice01.activeIduser = activeIduser;
    }

    /**
     * Gets k size (of KNN)
     *
     * @return k size (of KNN)
     */
    public static int getkSize() {
        return kSize;
    }

    /**
     * Sets k size (of KNN)
     *
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

        // RECOMMENDATIONS
        System.out.print("Calculating recommendations for user " + activeIduser + "... ");
        time = System.currentTimeMillis();

        Map<Integer, Double> recommendations = RecommenderUtils.RecommendationsKNN(activeIduser, kSize);

        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

        Practice01.PrintRecommendations(activeIduser, kSize, recommendations);

        for (Entry<Integer, Double> entry : recommendations.entrySet()) {
            System.out.println("Item: " + entry.getKey() + "\t Rating: " + entry.getValue());
        }
    }

    public static void PrintRecommendations(int idActiveUser, int kSize, Map<Integer, Double> recommendations) {
        FileWriter fileWriter = new FileWriter("Recommendations_User" + idActiveUser + "_k" + kSize + ".txt");
        fileWriter.Write("RECOMMENDATIONS");
        fileWriter.Write("Active user: " + idActiveUser + " | k = " + kSize + " | Time: " + Calendar.getInstance().getTime().toString());
        fileWriter.Write("");
        fileWriter.Write("ITEM\tRATING");
        for (Entry<Integer, Double> entry : recommendations.entrySet()) {
            fileWriter.Write(String.format("%4s", entry.getKey()) + "\t" + entry.getValue());
        }
        fileWriter.Close();
    }
}
