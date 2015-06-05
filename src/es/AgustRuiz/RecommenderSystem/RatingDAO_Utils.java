/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities for RatingDAO's
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingDAO_Utils {

    /**
     * Fills a Rating from a ResultSet
     *
     * @param rs ResultSet
     * @return Rating filled from rs
     * @throws SQLException
     */
    public static Rating fillRatings(ResultSet rs) throws SQLException {
        Rating rating = new Rating();
        rating.setIduser(rs.getInt("iduser"));
        rating.setIditem(rs.getInt("iditem"));
        rating.setRating(rs.getDouble("rating"));
        rating.setTimestamp(TimestampParser(rs.getString("timestamp")));
        return rating;
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
