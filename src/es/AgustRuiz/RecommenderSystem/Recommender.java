/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.sqrt;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Recommender {

    /**
     * Calculates similarity of two users
     * @param user1 User 1
     * @param user2 User 2
     * @return Similarity using Pearson coefficient
     */
    public static Float Similarity(User user1, User user2) {
        HashMap<Integer, Rating> ratingsUser1 = RatingDAO.getRatingsOfUser(user1.getIduser());
        HashMap<Integer, Rating> ratingsUser2 = RatingDAO.getRatingsOfUser(user2.getIduser());

        float avgUser1 = Recommender.avgRatings(ratingsUser1);
        float avgUser2 = Recommender.avgRatings(ratingsUser2);

        System.out.println("Media del usuario 1 " + user1.getIduser() + ": " + avgUser1);
        System.out.println("Media del usuario 2 " + user2.getIduser() + ": " + avgUser2);

        float dividend = 0f;
        float divisorSub1 = 0f;
        float divisorSub2 = 0f;
        
        for (Entry<Integer, Rating> entry1 : ratingsUser1.entrySet()) {
            if (ratingsUser2.containsKey(entry1.getKey())) {
                float valUser1 = entry1.getValue().getRating() - avgUser1;
                float valUser2 = ratingsUser2.get(entry1.getKey()).getRating() - avgUser2;
                dividend += (valUser1 * valUser2);
                divisorSub1 += (valUser1 * valUser1);
                divisorSub2 += (valUser2 * valUser2);
            }
        }
        
        float divisor = (float)(sqrt(divisorSub1*divisorSub2));
        
        return dividend / divisor;
    }

    /**
     * Calculate average of ratings 
     * @param ratingsMap HashMap<Integer, Rating> with ratings
     * @return Average of ratings
     */
    private static Float avgRatings(HashMap<Integer, Rating> ratingsMap) {
        Float avg = 0f;
        int count = 0;
        for (Entry<Integer, Rating> entry : ratingsMap.entrySet()) {
            ++count;
            avg += entry.getValue().getRating();
        }
        return avg / count;
    }

}
