package es.AgustRuiz.RecommenderSystem;

import java.sql.Timestamp;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Rating {
    
    /*** VARIABLES ***/
    
    /// User Id
    private Integer iduser = null; 
    
    /// Item Id
    private Integer iditem = null;
    
    /// Rating [1,5]
    private Double rating = null;
    
    /// Timestamp
    private Timestamp timestamp = null;
    
    /*** METHODS ***/
    
    /**
     * Constructor
     */
    public Rating(){
        this.InitializeVariables();
    }

    /**
     * Get iduser of the rating
     * @return iduser of the rating
     */
    public Integer getIduser() {
        return iduser;
    }

    /**
     * 
     * @param iduser 
     */
    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public Integer getIditem() {
        return iditem;
    }

    public void setIditem(Integer iditem) {
        this.iditem = iditem;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Initialize variables
     */
    private void InitializeVariables() {
        this.iduser = -1;
        this.iditem = -1;
        this.rating = 0.0;
        this.timestamp = new Timestamp(0);
    }
    
    /**
     * Get the Rating information in a string
     * @return String with Rating information
     */
    public String toString(){
        String s = "[RATE] Iduser: " + this.iduser +
                ", Iditem: " + this.iditem +
                ", Rating: " + this.rating +
                ", Timestamp: " + this.timestamp;
        return s;
    }
}
