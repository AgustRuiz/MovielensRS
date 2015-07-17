package es.AgustRuiz.RecommenderSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

/**
 * User DAO
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class UserHandler {
    
    private HashMap<Integer, User> usersMap = new HashMap();

    /**
     * Constructor
     */
    public UserHandler() {
        this.loadFromDb();
    }

    /**
     * Loads list of Users from database
     */
    private void loadFromDb() {
        try {
            PreparedStatement query = DbConnection.getConnection().prepareStatement("SELECT * FROM users");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = UserHandler.fillUser(rs);
                this.usersMap.put(user.getIduser(), user);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of users from database. " + e);
        }
    }

    /**
     * Gets an User
     *
     * @param iduser Item Id fo filter
     * @return The first item found with iditem or null otherwise
     */
    public User get(int iduser) {
        if(this.usersMap.containsKey(iduser)){
            return this.usersMap.get(iduser);
        }else{
            return null;
        }
    }
    
    public Collection<User> getCollection(){
        return this.usersMap.values();
    }

    /**
     * Counts numbers of Users
     *
     * @return Numbers of Users
     */
    public int count() {
        return this.usersMap.size();
    }

    /**
     * Fills an User from a ResultSet
     *
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
    
    /**
     * Checks if exist an user id
     * @param iduser
     * @return True if exists of false otherwise
     */
    public Boolean existIduser(int iduser){
        return this.usersMap.containsKey(iduser);
    }
}
