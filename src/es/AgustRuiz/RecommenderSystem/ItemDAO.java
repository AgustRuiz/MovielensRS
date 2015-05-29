package es.AgustRuiz.RecommenderSystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class ItemDAO {

    /// Connection to database
    static DbConnection connection = new DbConnection();

    /**
     * Gets the list of Items from database
     * @return HashMap<id, item> of Items from database
     */
    public static HashMap<Integer, Item> getList() {
        HashMap<Integer, Item> itemsMap = new HashMap();
        try {
            PreparedStatement query = connection.getConnection().prepareStatement("SELECT * FROM items");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Item item = ItemDAO.getItem(rs);
                itemsMap.put(item.getIditem(), item);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get list of items from database. " + e);
        }
        return itemsMap;
    }

    /**
     * Gets an Item from database
     * @param iditem Item Id fo filter
     * @return The first item found with iditem or null otherwise
     */
    public static Item get(int iditem) {
        Item item = null;
        try {
            PreparedStatement query = connection.getConnection().prepareStatement("SELECT * FROM items WHERE iditem = " + iditem);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                item = ItemDAO.getItem(rs);
            }
            rs.close();
            query.close();
        } catch (Exception e) {
            System.err.println("Can't get item " + iditem + " from database. " + e);
        }
        return item;
    }

    /**
     * Fills an Item from a ResultSet
     * @param rs ResultSet
     * @return Item filled from rs
     * @throws SQLException 
     */
    private static Item getItem(ResultSet rs) throws SQLException {
        Item item = new Item(Integer.parseInt(rs.getString("iditem")), rs.getString("name"));
        item.setActionGenre(booleanParser(rs.getString("Action")));
        item.setAdventureGenre(booleanParser(rs.getString("Adventure")));
        item.setAnimationGenre(booleanParser(rs.getString("Animation")));
        item.setChildrensGenre(booleanParser(rs.getString("Children's")));
        item.setComedyGenre(booleanParser(rs.getString("Comedy")));
        item.setCrimeGenre(booleanParser(rs.getString("Crime")));
        item.setDocumentaryGenre(booleanParser(rs.getString("Documentary")));
        item.setDramaGenre(booleanParser(rs.getString("Drama")));
        item.setFantasyGenre(booleanParser(rs.getString("Fantasy")));
        item.setFilmNoirGenre(booleanParser(rs.getString("Film-Noir")));
        item.setHorrorGenre(booleanParser(rs.getString("Horror")));
        item.setMusicalGenre(booleanParser(rs.getString("Musical")));
        item.setMysteryGenre(booleanParser(rs.getString("Mystery")));
        item.setRomanceGenre(booleanParser(rs.getString("Romance")));
        item.setSciFiGenre(booleanParser(rs.getString("Sci-Fi")));
        item.setThrillerGenre(booleanParser(rs.getString("Thriller")));
        item.setWarGenre(booleanParser(rs.getString("War")));
        item.setWesternGenre(booleanParser(rs.getString("Western")));
        return item;
    }

    /**
     * Returns a Boolean from a string
     * @param s String
     * @return true if s=="1", false otherwise
     */
    private static Boolean booleanParser(String s) {
        return (s.equals("1"));
    }
}
