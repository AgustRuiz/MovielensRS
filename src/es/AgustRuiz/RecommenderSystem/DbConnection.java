package es.AgustRuiz.RecommenderSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection to database
 *
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

    /// Connection status
    private static Boolean status = false;

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
        DbConnection.Connect();
        return connection;
    }

    /**
     * Get database name
     *
     * @return Database name
     */
    public static String getDb() {
        DbConnection.Connect();
        return DbConnection.db;
    }

    /**
     * Get connection username
     *
     * @return Username
     */
    public static String getUsername() {
        DbConnection.Connect();
        return DbConnection.username;
    }

    /**
     * Get connection password
     *
     * @return Password
     */
    public static String getPassword() {
        DbConnection.Connect();
        return DbConnection.password;
    }

    /**
     * Get URL connection
     *
     * @return URL connection
     */
    public static String getUrlConnection() {
        DbConnection.Connect();
        return DbConnection.url;
    }

    public static boolean isOk() {
        DbConnection.Connect();
        return DbConnection.status;
    }

    /**
     * Set connection
     */
    private static void Connect() {
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
                DbConnection.status = true;
//            } catch (SQLException e) {
//                System.err.println(e);
//            } catch (ClassNotFoundException e) {
//                System.err.println(e);
            } catch (Exception e) {
                DbConnection.status = false;
            }
        }
    }

    /**
     * Disconnects from database
     */
    private static void Disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            //Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        connection = null;
    }
}
