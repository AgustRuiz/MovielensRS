package es.AgustRuiz.RecommenderSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Rating Handler
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingTestHandler extends GenericRatingHandler {

    @Override
    void LoadFromDb() {
        this.ratingMap = new HashMap();
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM ratings_1_test");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                this.ratingMap.put(new Pair_UserItem(rs.getInt("iduser"), rs.getInt("iditem")), rs.getDouble("rating"));
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of ratings (test) from database. " + e);
        }
    }
    
    @Override
    Double GetAvgCoratingUser(int activeUserid, int referenceUserid) {
        if (!this.avgCorating.containsKey(activeUserid)) {
            this.avgCorating.put(activeUserid, new HashMap<Integer, Double>());
        }
        if(!this.avgCorating.get(activeUserid).containsKey(referenceUserid)){
            Double avgRating = null;
            try {
                PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT avg(rating) FROM ratings_1_test WHERE iduser=" + activeUserid + " AND iditem IN (SELECT iditem FROM ratings_1_test WHERE iduser=" + referenceUserid + ")");
                ResultSet rs = query.executeQuery();
                if (rs.next()) {
                    avgRating = rs.getDouble(1);
                }
                rs.close();
                query.close();
            } catch (Exception e) {
                System.err.println("Can't get ratings from database. " + e);
            }
            this.avgCorating.get(activeUserid).put(referenceUserid, avgRating);
        }
        return this.avgCorating.get(activeUserid).get(referenceUserid);
    }
}
