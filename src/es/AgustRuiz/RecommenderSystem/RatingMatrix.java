package es.AgustRuiz.RecommenderSystem;

import java.util.HashMap;

/**
 * Rating matrix
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingMatrix {

    /// Matrix
    private HashMap<Integer, HashMap<Integer, Float>> matrix;

    /**
     * Constructor
     */
    public RatingMatrix() {
    }

    /**
     * Set value in rating matrix
     * @param userid User Id
     * @param itemid Item Id
     * @param rating Rating
     */
    public void set(int userid, int itemid, float rating) {
        if (!matrix.containsKey(userid)) {
            matrix.put(userid, new HashMap<Integer, Float>());
        }
        matrix.get(userid).put(itemid, rating);
    }

    /**
     * Gets a rating for an user and an item
     * @param userid User Id
     * @param itemid Item Id
     * @return Rating if exist or null otherwise
     */
    public Float get(int userid, int itemid) {
        if (!matrix.containsKey(userid)) {
            return null;
        }else if(!matrix.get(userid).containsKey(itemid)){
            return null;
        }else{
            return matrix.get(userid).get(itemid);           
        }
    }
    
    /**
     * Unsets a rating for an user and an item
     * @param userid User Id
     * @param itemid Item Id
     */
    public void unset(int userid, int itemid){
        if (matrix.containsKey(userid) && matrix.get(userid).containsKey(itemid)) {
            matrix.get(userid).remove(itemid);
            if(matrix.get(userid).size()==0){
                matrix.remove(userid);
            }
        }
    }
}
