/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static es.AgustRuiz.RecommenderSystem.Main.trainingNeighborhoodHandler;
import static es.AgustRuiz.RecommenderSystem.Main.ratingsTrainingHandler;
import static es.AgustRuiz.RecommenderSystem.Main.ratingsTestHandler;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Practice02 {

    /// K values
    private static List<Integer> kSizes = new ArrayList();

    /**
     * Gets all k values in string format
     *
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
     *
     * @param k K value to check
     * @return True if is already registered of false otherwise
     */
    public static Boolean hasKSize(int k) {
        return kSizes.contains(k);
    }

    public static void Run() {

        long globalTime, time;
        globalTime = System.currentTimeMillis();

        Double mae;
        int counter;

        for (int kSize : kSizes) {
            System.out.print("Evaluating k = " + kSize + "... ");
            FileWriter fileWriter = new FileWriter("Evaluation_k" + kSize + ".txt");
            fileWriter.Write("NEIGHBORHOOD");
            fileWriter.Write("k = " + kSize + " | Time: " + Calendar.getInstance().getTime().toString());
            fileWriter.Write("");
            fileWriter.Write("[USER, ITEM]\t" + String.format("%18s", "PREDICTION") + "\t" + String.format("%18s", "REAL"));

            time = System.currentTimeMillis();

            mae = 0.0;
            counter = 0;

            int currentIdItem, activeIdUser;
            Double predictedRating, realRating;

            for (Entry<Pair_UserItem, Double> entryRating : ratingsTestHandler.ratingMap.entrySet()) {

                // Prediction
                currentIdItem = entryRating.getKey().getIditem();
                activeIdUser = entryRating.getKey().getIduser();

                // Ratings
                realRating = entryRating.getValue();
                predictedRating = RecommenderUtils.PredictRating_Training(activeIdUser, currentIdItem, kSize);

                if (predictedRating != null) {
                    mae += abs(predictedRating - realRating);
                    ++counter;
                    fileWriter.Write("[" + String.format("%4d", activeIdUser) + ", " + String.format("%4d", currentIdItem) + "]\t" + String.format("%17.16f", predictedRating) + "\t" + String.format("%17.16f", realRating));
                } else {
                    fileWriter.Write("[" + String.format("%4d", activeIdUser) + ", " + String.format("%4d", currentIdItem) + "]\t        ???       \t" + String.format("%17.16f", realRating));
                }
                // End Prediction

            }

            if (counter > 0) {
                mae = mae / counter;
            } else {
                mae = Double.POSITIVE_INFINITY;
            }

            fileWriter.Write("");
            fileWriter.Write("MAE = " + mae);
            fileWriter.Close();

            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms! (MAE=" + mae + ")");
            //break; // Force exit
        }
        System.out.println("ALL FINISHED IN " + (System.currentTimeMillis() - globalTime) + "ms!");
    }

}
