package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.abs;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Main class
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Main {

    private static final int K_VALUE = 20;
    private static final int USER_TEST = 23;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long time;

        User activeUser = UserDAO.get(USER_TEST);

        /* * * NEIGHBORHOOD * * */
        System.out.println("Building neighbourhood...");
        time = System.currentTimeMillis();

        HashMap<Double, User> neighborhoodSimilarityUser = Recommender.KNN(activeUser, K_VALUE);
        HashMap<Integer, Double> avgRatingsUsers = new HashMap();
        for (User user : neighborhoodSimilarityUser.values()) {
            int iduser = user.getIduser();
            avgRatingsUsers.put(iduser, RatingDAO.avgRatings(iduser));
        }
        
        System.out.println("Neighbourhood builded in " + (System.currentTimeMillis() - time) + " ms!\n");
        /* * * /NEIGHBORHOOD * * */

        /* * * RATINGS ACTIVE USER * * */
        System.out.println("Getting ratings for active user...");
        time = System.currentTimeMillis();

        HashMap<Integer, Rating> ratingsActiveUser = RatingDAO.getRatingsOfUser(USER_TEST);
        double avgRatingsActiveUser = RatingDAO.avgRatings(USER_TEST);

        System.out.println("Done in " + (System.currentTimeMillis() - time) + " ms!\n");
        /* * * /RATINGS ACTIVE USER * * */

        /* * * RATING MATRIX * * */
        System.out.println("Calculating similarity and rating matrix...");
        time = System.currentTimeMillis();

        HashMap<Integer, Double> similarityMap = new HashMap(); // HashMap<ItemId, Similarity>
        RatingMatrix ratingMatrix = new RatingMatrix();
        for (Entry<Double, User> entrySimilarUser : neighborhoodSimilarityUser.entrySet()) {
            int similarUserId = entrySimilarUser.getValue().getIduser();
            similarityMap.put(similarUserId, entrySimilarUser.getKey());
            for (Entry<Integer, Rating> entryIditemRating : RatingDAO.getRatingsOfUser(similarUserId).entrySet()) {
                ratingMatrix.set(entrySimilarUser.getValue().getIduser(), entryIditemRating.getKey(), entryIditemRating.getValue().getRating());
            }
        }

        System.out.println("Similarity and rating matrix builded in " + (System.currentTimeMillis() - time) + " ms!\n");
        /* * * /RATING MATRIX * * */

        /* * * RECOMMENDATIONS * * */
        System.out.println("Calculating recommendations...");
        time = System.currentTimeMillis();

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
                    System.err.println("");
                }

            }
        }

        System.out.println("Finished in " + (System.currentTimeMillis() - time) + " ms!\n");
        /* * * /RECOMMENDATIONS * * */

        /* * * RESULTS * * */
        System.out.println("RESULTS:");
        for (Entry<Item, Double> entry : recommendations.entrySet()) {
            System.out.println("idItem:\t" + entry.getKey().getIditem() + "\t->\t" + entry.getValue());
        }
        System.out.println("Num of recommendations: " + recommendations.size());
        /* * * /RESULTS * * */

    }

}
