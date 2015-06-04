/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

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
}
