/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Recommender {

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

        HashMap<Integer, Rating> ratingsUser1 = RatingDAO.getRatingsOfUser(user1Id);
        HashMap<Integer, Rating> ratingsUser2 = RatingDAO.getRatingsOfUser(user2Id);

        double avgUser1 = RatingDAO.avgRatings(user1Id);
        double avgUser2 = RatingDAO.avgRatings(user2Id);

        double dividend = 0f;
        double divisorSub1 = 0f;
        double divisorSub2 = 0f;

        for (Entry<Integer, Rating> entryRating1 : ratingsUser1.entrySet()) {
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
                similarity = Recommender.Similarity(activeUser, currentUser);
                if (similarity > 0) {
                    similarUsersMap.put(similarity, currentUser);
                }
            }
        }

        //System.out.println(usuariosSimilares.size());
        HashMap<Double, User> neighbor = new HashMap();

        int i = 0;
        for (Double key : similarUsersMap.keySet()) {
            neighbor.put(key, similarUsersMap.get(key));
            ++i;
            if (i == K) {
                break;
            }
        }
        return neighbor;
    }
    
    public static HashMap<Item, Double> makeRecomendations(User activeUser, int neigborhoodSize){
        long time;
        int activeUserId = activeUser.getIduser();
        
        /* * * NEIGHBORHOOD * * */
        System.out.println("Building neighbourhood...");
        time = System.currentTimeMillis();

        HashMap<Double, User> neighborhoodSimilarityUser = Recommender.KNN(activeUser, neigborhoodSize);
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

        HashMap<Integer, Rating> ratingsActiveUser = RatingDAO.getRatingsOfUser(activeUserId);
        double avgRatingsActiveUser = RatingDAO.avgRatings(activeUserId);

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
                }

            }
        }

        System.out.println("Finished in " + (System.currentTimeMillis() - time) + " ms!\n");
        /* * * /RECOMMENDATIONS * * */
        
        return recommendations;
    }

    /**
     * Calculate average of ratings
     *
     * @param ratingsMap HashMap<Integer, Rating> with ratings
     * @return Average of ratings
     */
    private static Double avgRatings(HashMap<Integer, Rating> ratingsMap) {
        double avg = 0f;
        int count = 0;
        for (Entry<Integer, Rating> entry : ratingsMap.entrySet()) {
            ++count;
            avg += entry.getValue().getRating();
        }
        return avg / count;
    }
    
    public static void Run(){
        
        User activeUser = UserDAO.get(Main.ACTIVE_USER);

        Main.recommendations = Recommender.makeRecomendations(activeUser, Main.K_VALUE);
        
        System.out.println("RESULTS:");
        for (Entry<Item, Double> entry : Main.recommendations.entrySet()) {
            System.out.println("idItem:\t" + entry.getKey().getIditem() + "\t->\t" + entry.getValue());
        }
        System.out.println("Num of recommendations: " + Main.recommendations.size());
    }
}
