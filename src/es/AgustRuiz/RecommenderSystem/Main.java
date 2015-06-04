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

        User activeUser = UserDAO.get(USER_TEST);

        HashMap<Item, Double> recommendations = Recommender.makeRecomendations(activeUser, K_VALUE);
        
        /* * * RESULTS * * */
        System.out.println("RESULTS:");
        for (Entry<Item, Double> entry : recommendations.entrySet()) {
            System.out.println("idItem:\t" + entry.getKey().getIditem() + "\t->\t" + entry.getValue());
        }
        System.out.println("Num of recommendations: " + recommendations.size());
        /* * * /RESULTS * * */

    }

}
