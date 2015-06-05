package es.AgustRuiz.RecommenderSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection to database
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class DbConnection {

    /// Database name
    private static String db;

    /// Username
    private static String username;

    /// Password
    private static String password;

    /// Connection URL
    private static String url;

    /// Connection
    private static Connection connection = null;

    /// XMLHandler
    private static XMLHandler configXML;

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
        if (connection == null) {
            DbConnection.configXML = new XMLHandler(("./DbConnection.xml"));
            if (configXML.isOK()) {
                DbConnection.db = DbConnection.configXML.getNode("DataBase");
                DbConnection.username = DbConnection.configXML.getNode("UserName");
                DbConnection.password = DbConnection.configXML.getNode("Password");
                DbConnection.url = DbConnection.configXML.getNode("UrlConnection");
            }
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
