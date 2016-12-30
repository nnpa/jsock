/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.db;

import conf.JConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database connection class
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class DBConnection {
    /**
     * Database name
     */
    public static String dbName;
    /**
     * Connection
     */
    public static Connection connection;
    /**
     * Statement
     */
    public static Statement statement;
    /**
     * DBConnection instance singleton
     */
    private static volatile DBConnection instance;

    /**
     * Create instance
     *
     * @return
     */
    public static DBConnection getInstance() {
        DBConnection localInstance = instance;

        if (localInstance == null) {
            synchronized (DBConnection.class) {
                localInstance = instance;
                if (localInstance == null) {

                    instance = localInstance = new DBConnection();
                }
            }
        }
        return localInstance;
    }

    /**
     * database connection
     */
    public DBConnection() {
        try {
            connection = DriverManager.getConnection(JConfig.mysql_url, JConfig.mysql_user, JConfig.mysql_password);
            dbName = JConfig.mysql_db_name;
            statement = connection.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            connection.close();
            statement = null;
            instance = null;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
