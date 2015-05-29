package es.AgustRuiz.RecommenderSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connection to database
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class DbConnection {

    /// Database name
    private static String db = "ml-100k-mysql";

    /// Username
    private final static String username = "root";

    /// Password
    private final static String password = "";

    /// Connection URL
    private final static String url = "jdbc:mysql://localhost:3306/" + db + "?zeroDateTimeBehavior=convertToNull";

    /// Connection
    private static Connection connection = null;

    /**
     * Constructor
     */
    private DbConnection() {
    }

    /**
     * Gets connection
     *
     * @return Connection to database
     */
    public static Connection getConnection() {
        if (connection == null ) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                if (connection != null) {
                    //System.out.println("Conection to " + db + " OK");
                }
            } catch (SQLException e) {
                System.err.println(e);
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return connection;
    }

    /**
     * Disconnects from database
     */
    private void Disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            //Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        connection = null;
    }

}
