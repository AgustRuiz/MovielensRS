/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Rating DAO for test
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingTestDAO {

    /**
     * Get a rating of an User for an Item (TEST)
     *
     * @param iduser User Id
     * @param iditem Item Id
     * @return Rating if exist or null otherwise
     */
    public static Rating get(int iduser, int iditem) {
        Rating rating = null;
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings_1_test WHERE iduser =" + iduser + " AND iditem=" + iditem);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                rating = RatingDAO_Utils.fillRatings(rs);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings (test) from database. " + e);
        }
        return rating;
    }

    /**
     * Get all a user ratings (TEST)
     *
     * @param iduser User id
     * @return HashMap<Iditem, Rating> of ratings of the User
     */
    public static HashMap<Integer, Rating> getRatingsOfUser(int iduser) {
        HashMap<Integer, Rating> ratingMap = new HashMap();
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings_1_test WHERE iduser =" + iduser);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Rating rating = RatingDAO_Utils.fillRatings(rs);
                ratingMap.put(rating.getIditem(), rating);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings (training) from database. " + e);
        }
        return ratingMap;
    }

    /**
     * Gets the list of ratings from database (TEST)
     *
     * @return List of ratings from database
     */
    public static List<Rating> getList() {
        List<Rating> ratingsList = new ArrayList();
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings_1_test");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                ratingsList.add(RatingDAO_Utils.fillRatings(rs));
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings (test) from database. " + e);
        }
        return ratingsList;
    }
    
    /**
     * Gets average of ratings of an user (TEST)
     * @param iduser User id
     * @return Average of ratings
     */
    public static Double avgRatings(int iduser) {
        Double avgRatings = null;
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT AVG(rating) FROM `ratings_1_test` WHERE iduser=" + iduser);
            ResultSet rs = query.executeQuery();
            rs.next();
            avgRatings = rs.getDouble(1);
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get avg of ratings (test) from database. " + e);
        }
        return avgRatings;
    }
}
