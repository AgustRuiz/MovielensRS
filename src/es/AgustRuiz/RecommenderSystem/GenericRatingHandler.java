/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public abstract class GenericRatingHandler {
    
    protected HashMap<Pair_UserItem, Double> ratingMap;
    protected HashMap<Integer, Double> avgUserRatings;
    protected HashMap<Integer, Double> avgItemRatings;
    protected HashMap<Integer, HashMap<Integer, Double> > avgCorating;

    /**
     * Constructor
     */
    public GenericRatingHandler() {
        LoadFromDb();
        this.CalculateAvgUserRating();
        this.CalculateAvgItemRating();
        this.avgCorating = new HashMap<>();
    }

    /**
     * Loads list of ratings from database
     *
     * @return List of ratings from database
     */
    abstract void LoadFromDb();

    /**
     * Loads list of ratings from database
     *
     * @return List of ratings from database
     */
    abstract Double GetAvgCoratingUser(int activeUserid, int referenceUserid);

    /**
     * Calculate avg ratings for users
     */
    protected void CalculateAvgUserRating() {
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
    
    protected void CalculateAvgItemRating(){
        if (this.avgItemRatings == null) {
            this.avgItemRatings = new HashMap();
        }
        if (this.ratingMap.size() > 0) {
            HashMap<Integer, Double> accumulatorMap = new HashMap();
            HashMap<Integer, Integer> counterMap = new HashMap();
            int currentIditem;
            for (Map.Entry<Pair_UserItem, Double> e : this.ratingMap.entrySet()) {
                currentIditem = e.getKey().iditem;
                if (accumulatorMap.containsKey(currentIditem)) {
                    accumulatorMap.put(currentIditem, accumulatorMap.get(currentIditem) + e.getValue());
                    counterMap.put(currentIditem, counterMap.get(currentIditem) + 1);
                } else {
                    accumulatorMap.put(currentIditem, e.getValue());
                    counterMap.put(currentIditem, 1);
                }
            }
            for (int iditem : accumulatorMap.keySet()) {
                this.avgItemRatings.put(iditem, accumulatorMap.get(iditem) / counterMap.get(iditem));
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
    public Double Get(int iduser, int iditem) {
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
    public int Count() {
        return this.ratingMap.size();
    }
    
    public Set<Map.Entry<Pair_UserItem, Double>> getEntrySet() {
        return this.ratingMap.entrySet();
    }
    
    public Double GetAvgRatingsUser(int iduser) {
        if (this.avgUserRatings.containsKey(iduser)) {
            return this.avgUserRatings.get(iduser);
        }else{
            return null;
        }
    }
    
    public Double GetAvgRatingsItem(int iditem) {
        if (this.avgItemRatings.containsKey(iditem)) {
            return this.avgItemRatings.get(iditem);
        }else{
            return null;
        }
    }
}
