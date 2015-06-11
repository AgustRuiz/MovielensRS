package es.AgustRuiz.RecommenderSystem;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Rating Handler
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingHandler {
    
    private HashMap<Pair_UserItem, Double> ratingMap;
    private HashMap<Integer, Double> avgUserRatings;

    /**
     * Constructor
     */
    public RatingHandler() {
        this.loadFromDb();
        this.calculateAvgUserRating();
    }

    /**
     * Loads list of ratings from database
     *
     * @return List of ratings from database
     */
    private void loadFromDb() {
        this.ratingMap = new HashMap();
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                this.ratingMap.put(new Pair_UserItem(rs.getInt("iduser"), rs.getInt("iditem")), rs.getDouble("rating"));
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings from database. " + e);
        }
    }

    /**
     * Calculate avg ratings for users
     */
    private void calculateAvgUserRating() {
        if (this.avgUserRatings == null) {
            this.avgUserRatings = new HashMap();
        }
        if (this.ratingMap.size() > 0) {
            HashMap<Integer, Double> accumulatorMap = new HashMap();
            HashMap<Integer, Integer> counterMap = new HashMap();
            int currentIduser;
            for (Entry<Pair_UserItem, Double> e : this.ratingMap.entrySet()) {
                currentIduser = e.getKey().iduser;
                if (accumulatorMap.containsKey(currentIduser)) {
                    accumulatorMap.put(currentIduser, accumulatorMap.get(currentIduser) + e.getValue());
                    counterMap.put(currentIduser, counterMap.get(currentIduser) + 1);
                } else {
                    accumulatorMap.put(currentIduser, e.getValue());
                    counterMap.put(currentIduser, 1);
                }
            }
            for (int iduser : accumulatorMap.keySet()) {
                this.avgUserRatings.put(iduser, accumulatorMap.get(iduser) / counterMap.get(iduser));
            }
        }
    }

    /**
     * Gets a rating for an user and an item
     *
     * @param useritem Pair user-item
     * @return Rating or null if not exists
     */
    public Double get(int iduser, int iditem) {
        Pair_UserItem pair = new Pair_UserItem(iduser, iditem);
        if (this.ratingMap.containsKey(pair)) {
            return this.ratingMap.get(pair);
        } else {
            return null;
        }
    }

    /**
     * Get number of ratings
     *
     * @return Number of ratings
     */
    public int count() {
        return this.ratingMap.size();
    }
    
    public Set<Entry<Pair_UserItem, Double>> getEntrySet() {
        return this.ratingMap.entrySet();
    }
    
    public Double getAvgRatingsUser(int iduser) {
        if (this.avgUserRatings.containsKey(iduser)) {
            return this.avgUserRatings.get(iduser);
        }else{
            return null;
        }
    }
}