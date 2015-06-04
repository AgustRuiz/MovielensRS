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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingDAO {

    /**
     * Get a rating of an User for an Item
     *
     * @param iduser User Id
     * @param iditem Item Id
     * @return Rating if exist or null otherwise
     */
    public static Rating get(int iduser, int iditem) {
        Rating rating = null;
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings WHERE iduser =" + iduser + " AND iditem=" + iditem);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                rating = RatingDAO.fillRatings(rs);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings from database. " + e);
        }
        return rating;
    }

    /**
     * Get all a user ratings
     *
     * @param iduser User id
     * @return HashMap<Iditem, Rating> of ratings of the User
     */
    public static HashMap<Integer, Rating> getRatingsOfUser(int iduser) {
        HashMap<Integer, Rating> ratingMap = new HashMap();
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings WHERE iduser =" + iduser);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Rating rating = RatingDAO.fillRatings(rs);
                ratingMap.put(rating.getIditem(), rating);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings from database. " + e);
        }
        return ratingMap;
    }

    /**
     * Gets the list of Items from database
     *
     * @return List of items from database
     */
    public static List<Rating> getList() {
        List<Rating> ratingsList = new ArrayList();
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                ratingsList.add(RatingDAO.fillRatings(rs));
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings from database. " + e);
        }
        return ratingsList;
    }

    /**
     * Fills a Rating from a ResultSet
     *
     * @param rs ResultSet
     * @return Rating filled from rs
     * @throws SQLException
     */
    private static Rating fillRatings(ResultSet rs) throws SQLException {
        Rating rating = new Rating();
        rating.setIduser(rs.getInt("iduser"));
        rating.setIditem(rs.getInt("iditem"));
        rating.setRating(rs.getDouble("rating"));
        rating.setTimestamp(TimestampParser(rs.getString("timestamp")));
        return rating;
    }
    
    /**
     * Gets average of ratings of an user
     * @param iduser User id
     * @return Average of ratings
     */
    public static Double avgRatings(int iduser) {
        Double avgRatings = null;
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT AVG(rating) FROM `ratings` WHERE iduser=" + iduser);
            ResultSet rs = query.executeQuery();
            rs.next();
            avgRatings = rs.getDouble(1);
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get avg of ratings from database. " + e);
        }
        return avgRatings;
    }

    /**
     * Timestamp parser
     *
     * @param timestampString String timestamp
     * @return timestampString converted to a Timestamp
     */
    private static Timestamp TimestampParser(String timestampString) {
        Timestamp timestamp = null;
        if (timestampString == null) {
            timestamp = new Timestamp(0);
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date parsedDate = dateFormat.parse(timestampString);
                timestamp = new Timestamp(parsedDate.getTime());
            } catch (ParseException ex) {
                timestamp = new Timestamp(0);
            }
        }
        return timestamp;
    }
}
