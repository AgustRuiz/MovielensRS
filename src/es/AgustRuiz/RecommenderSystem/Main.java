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

    /// K value (for KNN)
    public static int K_VALUE = 20;
    
    /// Active user id
    public static int ACTIVE_USER = 23;
    
    /// Recommendations
    public static HashMap<Item, Double> recommendations;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI.MainMenu();
    }

}
