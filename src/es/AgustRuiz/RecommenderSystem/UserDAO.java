package es.AgustRuiz.RecommenderSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class UserDAO {

    /**
     * Gets the list of Items from database
     * @return HashMap<id, user> of Items from database
     */
    public static HashMap<Integer, User> getList() {
        HashMap<Integer, User> usersMap = new HashMap();
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM users");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = UserDAO.fillUser(rs);
                usersMap.put(user.getIduser(), user);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of users from database. " + e);
        }
        return usersMap;
    }

    /**
     * Gets an Item from database
     * @param iduser Item Id fo filter
     * @return The first item found with iditem or null otherwise
     */
    public static User get(int iduser) {
        User user = null;
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM users WHERE iduser = " + iduser);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                user = UserDAO.fillUser(rs);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get user " + iduser + " from database. " + e);
        }
        return user;
    }
    
    /**
     * Counts numbers of Users
     * @return Numbers of Users
     */
    public static Integer count(){
        Integer size = -1;
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT COUNT(*) FROM users");
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get size of Users table from database. " + e);
        }
        return size;
    }
    
    /**
     * Fills an User from a ResultSet
     * @param rs ResultSet
     * @return User filled from rs
     * @throws SQLException 
     */
    private static User fillUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setIduser(Integer.parseInt(rs.getString("iduser")));
        user.setName(rs.getString("name"));
        user.setAge(Integer.parseInt(rs.getString("age")));
        user.setGender(rs.getString("gender").charAt(0));
        user.setOccupation(rs.getString("occupation"));
        user.setZipcode(rs.getString("zipcode"));
        
        return user;
    }
    
}
