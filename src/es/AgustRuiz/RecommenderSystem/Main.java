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

    public static UserHandler usersHandler;
    public static ItemHandler itemsHandler;
    public static RatingHandler ratingsHandler;
    public static SimilarityHandler similarityHandler;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!DbConnection.isOk()) {
            System.err.println("Can't connect to database!");
        } else {
            long time;

            // USERS
            System.out.print("Loading users... ");
            time = System.currentTimeMillis();
            usersHandler = new UserHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // ITEMS
            System.out.print("Loading items... ");
            time = System.currentTimeMillis();
            itemsHandler = new ItemHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // RATINGS 
            System.out.print("Loading ratings and building rating matrix... ");
            time = System.currentTimeMillis();
            ratingsHandler = new RatingHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // SIMILARITY HANDLER
            System.out.print("Building similarity handler... ");
            time = System.currentTimeMillis();
            similarityHandler = new SimilarityHandler(itemsHandler, usersHandler, ratingsHandler);
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");
            
            
            GUI.MainMenu();
        }
    }

}
