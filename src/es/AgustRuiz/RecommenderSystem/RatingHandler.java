package es.AgustRuiz.RecommenderSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Rating Handler
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class RatingHandler extends GenericRatingHandler{

    @Override
    void LoadFromDb() {
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
}
