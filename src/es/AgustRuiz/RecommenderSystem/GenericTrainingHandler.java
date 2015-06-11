/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public abstract class GenericTrainingHandler {
    
    protected HashMap<Pair_UserItem, Double> ratingMap;
    protected HashMap<Integer, Double> avgUserRatings;

    /**
     * Constructor
     */
    public GenericTrainingHandler() {
        loadFromDb();
        this.calculateAvgUserRating();
    }

    /**
     * Loads list of ratings from database
     *
     * @return List of ratings from database
     */
    abstract void loadFromDb();

    /**
     * Calculate avg ratings for users
     */
    protected void calculateAvgUserRating() {
        if (this.avgUserRatings == null) {
            this.avgUserRatings = new HashMap();
        }
        if (this.ratingMap.size() > 0) {
            HashMap<Integer, Double> accumulatorMap = new HashMap();
            HashMap<Integer, Integer> counterMap = new HashMap();
            int currentIduser;
            for (Map.Entry<Pair_UserItem, Double> e : this.ratingMap.entrySet()) {
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
     * @param iduser User id
     * @param iditem Item id
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
    
    public Set<Map.Entry<Pair_UserItem, Double>> getEntrySet() {
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
