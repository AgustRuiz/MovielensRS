/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
    public static List<Integer> getKValues() {
        return kValues;
    }

    public static Boolean hasKValue(int kvalue) {
        return kValues.contains(kvalue);
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
     *
     * @return Active user id
     */
    public static int getActiveUserId() {
        return Recommender_Evaluator.activeUserId;
    }

    /**
     * Set active user id
     *
     * @param iduser Active user id
     */
    public static void setActiveUserId(int iduser) {
        Recommender_Evaluator.activeUserId = iduser;
    }

    /**
     * Run recommender and evaluation
     */
    public static void Run() {
        if (Recommender_Evaluator.kValues.size() < 2) {
            System.out.println("You need, at least, 2 K values for KNN evaluation\n");
        } else {
            System.out.println("\nRunning experiments...");
            ResultsEvaluation resultsEvaluation = new ResultsEvaluation();
            double mae;
            long time;
            long timeGlobal = System.currentTimeMillis();

            /* * * RATINGS ACTIVE USER * * */
            System.out.print("Getting ratings for active user... ");
            time = System.currentTimeMillis();
            HashMap<Integer, Rating> ratingsActiveUser = RatingTrainingDAO.getRatingsOfUser(activeUserId);
            double avgRatingsActiveUser = RatingTrainingDAO.avgRatings(activeUserId);
            System.out.println("Done in " + (System.currentTimeMillis() - time) + " ms!");
            /* * * /RATINGS ACTIVE USER * * */

            for (int currentK : Recommender_Evaluator.getKValues()) {
                time = System.currentTimeMillis();
                User activeUser = UserDAO.get(Recommender_Evaluator.getActiveUserId());
                HashMap<Item, Double> currentRecommendations = Recommender_Evaluator.makeRecommendations(activeUser, currentK, ratingsActiveUser, avgRatingsActiveUser, false);
                time = System.currentTimeMillis() - time;
                mae = Recommender_Evaluator.MAE(currentRecommendations);
                resultsEvaluation.addEvaluation(currentK, time, mae);
                System.out.println("\nExperiment K=" + currentK + " finished in " + time + " ms!");
            }
            System.out.println("\nEverything finished in " + (System.currentTimeMillis() - timeGlobal) + " ms!\n");

            System.out.println(resultsEvaluation.getResults());
        }
    }

    /**
     *
     * @param activeUser
     * @param neigborhoodSize
     * @param printMessages
     * @return
     */
    public static HashMap<Item, Double> makeRecommendations(User activeUser, int neigborhoodSize, HashMap<Integer, Rating> ratingsActiveUser, double avgRatingsActiveUser, Boolean printMessages) {
        long time = 0;
        int activeUserId = activeUser.getIduser();

        /* * * NEIGHBORHOOD * * */
        if (printMessages) {
            System.out.print("\nBuilding neighbourhood... ");
            time = System.currentTimeMillis();
        }

        HashMap<Double, User> neighborhoodSimilarityUser = Recommender_Evaluator.KNN(activeUser, neigborhoodSize);
        HashMap<Integer, Double> avgRatingsUsers = new HashMap();
        for (User user : neighborhoodSimilarityUser.values()) {
            int iduser = user.getIduser();
            avgRatingsUsers.put(iduser, RatingDAO.avgRatings(iduser));
        }
        if (printMessages) {
            System.out.println("Neighbourhood builded in " + (System.currentTimeMillis() - time) + " ms!");
        }
        /* * * /NEIGHBORHOOD * * */

        /* * * RATING MATRIX * * */
        if (printMessages) {
            System.out.print("Calculating similarity and rating matrix... ");
            time = System.currentTimeMillis();
        }
        HashMap<Integer, Double> similarityMap = new HashMap(); // HashMap<ItemId, Similarity>
        RatingMatrix ratingMatrix = new RatingMatrix();
        for (Map.Entry<Double, User> entrySimilarUser : neighborhoodSimilarityUser.entrySet()) {
            int similarUserId = entrySimilarUser.getValue().getIduser();
            similarityMap.put(similarUserId, entrySimilarUser.getKey());
            for (Map.Entry<Integer, Rating> entryIditemRating : RatingTrainingDAO.getRatingsOfUser(similarUserId).entrySet()) {
                ratingMatrix.set(entrySimilarUser.getValue().getIduser(), entryIditemRating.getKey(), entryIditemRating.getValue().getRating());
            }
        }
        if (printMessages) {
            System.out.println("Similarity and rating matrix builded in " + (System.currentTimeMillis() - time) + " ms!");
        }
        /* * * /RATING MATRIX * * */

        /* * * RECOMMENDATIONS * * */
        if (printMessages) {
            System.out.print("Calculating recommendations... ");
            time = System.currentTimeMillis();
        }

        HashMap<Item, Double> recommendations = new HashMap();
        HashMap<Integer, Item> itemHashMap = ItemDAO.getList();

        for (Item currentItem : itemHashMap.values()) {
            int currentItemId = currentItem.getIditem();
            if (!ratingsActiveUser.containsKey(currentItemId)) {

                double dividend, divisor, similarity;
                dividend = divisor = similarity = 0;

                for (int currentUserId : similarityMap.keySet()) {
                    Double currentRating = ratingMatrix.get(currentUserId, currentItemId);
                    if (currentRating == null) {
                        continue;
                    }
                    similarity = similarityMap.get(currentUserId);
                    dividend += similarity * (currentRating - avgRatingsUsers.get(currentUserId));
                    divisor += abs(similarity);
                }
                if (divisor != 0) {
                    double predictedRating = avgRatingsActiveUser + (dividend / divisor);
                    recommendations.put(currentItem, predictedRating);
                }
            }
        }
        if (printMessages) {
            System.out.println("Finished in " + (System.currentTimeMillis() - time) + " ms!");
        }
        /* * * /RECOMMENDATIONS * * */

        return recommendations;
    }

    /**
     * Return the K nearest neighbors
     *
     * @param activeUser Active user
     * @param K Size of neighbours
     * @return HashMap<Similarity, User> with the K Nearest Neighbours to the
     * active User
     */
    public static HashMap<Double, User> KNN(User activeUser, int K) {
        Map<Double, User> similarUsersMap = new TreeMap(Collections.reverseOrder());

        double similarity;
        int activeUserId = activeUser.getIduser();

        for (User currentUser : UserDAO.getList().values()) {
            if (!currentUser.getIduser().equals(activeUserId)) {
                similarity = Recommender_Evaluator.Similarity(activeUser, currentUser);
                if (similarity > 0) {
                    similarUsersMap.put(similarity, currentUser);
                }
                //System.err.println("Similarity with " + currentUser.getIduser() + "\t: " + similarity);
            }
        }

        //System.out.println(usuariosSimilares.size());
        HashMap<Double, User> neighbor = new HashMap();

        int i = 0;
        for (Double key : similarUsersMap.keySet()) {
            //System.err.println("Similarity with " + similarUsersMap.get(key).getIduser() + "\t: " + key);
            neighbor.put(key, similarUsersMap.get(key));
            ++i;
            if (i == K) {
                break;
            }
        }
        return neighbor;
    }

    /**
     * Calculates similarity of two users
     *
     * @param user1 User 1
     * @param user2 User 2
     * @return Similarity using Pearson's coefficient
     */
    public static Double Similarity(User user1, User user2) {

        int user1Id = user1.getIduser();
        int user2Id = user2.getIduser();

        HashMap<Integer, Rating> ratingsUser1 = RatingTrainingDAO.getRatingsOfUser(user1Id);
        HashMap<Integer, Rating> ratingsUser2 = RatingTrainingDAO.getRatingsOfUser(user2Id);

        double avgUser1 = RatingTrainingDAO.avgRatings(user1Id);
        double avgUser2 = RatingTrainingDAO.avgRatings(user2Id);

        double dividend = 0f;
        double divisorSub1 = 0f;
        double divisorSub2 = 0f;

        for (Map.Entry<Integer, Rating> entryRating1 : ratingsUser1.entrySet()) {
            if (ratingsUser2.containsKey(entryRating1.getKey())) {
                Rating rating1 = entryRating1.getValue();
                Rating rating2 = ratingsUser2.get(entryRating1.getKey());

                double verde = rating1.getRating() - avgUser1;
                double naranja = rating2.getRating() - avgUser2;

                dividend += verde * naranja;
                divisorSub1 += verde * verde;
                divisorSub2 += naranja * naranja;
            }
        }
        return dividend / (sqrt(divisorSub1) * sqrt(divisorSub2));
    }

    public static double MAE(HashMap<Item, Double> recommendations) {
        int iduser = Recommender_Evaluator.activeUserId;
        Rating r;
        double mae = 0;
        int count = 0;
        for (Entry<Item, Double> entry : recommendations.entrySet()) {
            r = RatingTestDAO.get(iduser, entry.getKey().getIditem());
            if (r != null) {
                //System.err.println("Diferencia: " + (r.getRating() - entry.getValue()));
                mae = abs(r.getRating() - entry.getValue());
                ++count;
            }
        }
        return (mae / count);
    }
}
