package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
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

    private static final int ACTIVE_ID_USER = 23;
    private static final int K_SIZE = 20;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long time;

        //GUI.MainMenu();
        // USERS
        System.out.print("Loading users... ");
        time = System.currentTimeMillis();
        UserHandler users = new UserHandler();
        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

        // ITEMS
        System.out.print("Loading items... ");
        time = System.currentTimeMillis();
        ItemHandler items = new ItemHandler();
        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

        // RATINGS 
        System.out.print("Loading ratings and building rating matrix... ");
        time = System.currentTimeMillis();
        RatingHandler ratings = new RatingHandler();
        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

        //NEIGHBORHOOD
        System.out.print("Calculating neighborhood for user " + ACTIVE_ID_USER + " (" + K_SIZE + "-nn)... ");
        time = System.currentTimeMillis();
        SimilarityHandler similarityHandler = new SimilarityHandler(items, users, ratings);
        Map<Double, Integer> neighborhood = similarityHandler.calculateKNN(ACTIVE_ID_USER, K_SIZE);
        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

        // RECOMMENDATIONS
        System.out.print("Calculating recommendations for user " + ACTIVE_ID_USER + "... ");
        time = System.currentTimeMillis();
        
        Map<Double, Integer> recommendations = RecommenderUtils.MakeRecommendations(users, items, ratings, ACTIVE_ID_USER, neighborhood);
        
        System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");
        
        for(Entry<Double, Integer> entry : recommendations.entrySet()){
            System.out.println("Item: " + entry.getValue() + "\t Rating: " + entry.getKey());
        }

    }

}
