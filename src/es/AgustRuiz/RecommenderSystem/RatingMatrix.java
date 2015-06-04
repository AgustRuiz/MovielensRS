package es.AgustRuiz.RecommenderSystem;

import java.util.HashMap;

/**
 * Rating matrix
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingMatrix {

    /// Matrix < Iditem, < Iduser, Rating > >
    private HashMap<Integer, HashMap<Integer, Double>> matrix;

    /**
     * Constructor
     */
    public RatingMatrix() {
        matrix = new HashMap<>();
    }

    /**
     * Set value in rating matrix
     * @param userid User Id
     * @param itemid Item Id
     * @param rating Rating
     */
    public void set(int userid, int itemid, double rating) {
        if (!matrix.containsKey(itemid)) {
            matrix.put(itemid, new HashMap<Integer, Double>());
        }
        matrix.get(itemid).put(userid, rating);
    }

    /**
     * Gets a rating for an user and an item
     * @param userid User Id
     * @param itemid Item Id
     * @return Rating if exist or null otherwise
     */
    public Double get(int userid, int itemid) {
        if (!matrix.containsKey(itemid)) {
            return null;
        }else if(!matrix.get(itemid).containsKey(userid)){
            return null;
        }else{
            return matrix.get(itemid).get(userid);           
        }
    }
    
    public HashMap<Integer, Double> get(int itemid){
        if (matrix.containsKey(itemid)) {
            return matrix.get(itemid);
        }else{
            return null;
        }
    }
    
    /**
     * Unsets a rating for an user and an item
     * @param userid User Id
     * @param itemid Item Id
     */
    public void unset(int userid, int itemid){
        if (matrix.containsKey(itemid) && matrix.get(itemid).containsKey(userid)) {
            matrix.get(itemid).remove(userid);
            if(matrix.get(itemid).isEmpty()){
                matrix.remove(itemid);
            }
        }
    }
}
