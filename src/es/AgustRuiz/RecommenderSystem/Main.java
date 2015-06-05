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

    public static int K_VALUE = 20;
    public static int ACTIVE_USER = 23;
    public static HashMap<Item, Double> recommendations;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GUI.MainMenu();
        
        
        /*
        User activeUser = UserDAO.get(ACTIVE_USER);

        HashMap<Item, Double> recommendations = Recommender.makeRecomendations(activeUser, K_VALUE);
        
        System.out.println("RESULTS:");
        for (Entry<Item, Double> entry : recommendations.entrySet()) {
            System.out.println("idItem:\t" + entry.getKey().getIditem() + "\t->\t" + entry.getValue());
        }
        System.out.println("Num of recommendations: " + recommendations.size());
        
        /**/

    }

}
