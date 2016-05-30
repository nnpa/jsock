/*
 * jsock framework https://github.com/Padaboo/jsock open source
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
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class DBConnection{
    public static String dbName;
    
    public static Connection connection;
    
    public static Statement  statement;

    private static volatile DBConnection instance;

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


    public  DBConnection(){
        try {
            connection = DriverManager.getConnection(JConfig.mysql_url, JConfig.mysql_user, JConfig.mysql_password);
            dbName     = JConfig.mysql_db_name; 
            statement  = connection.createStatement();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            connection.close();
            statement = null;
            instance  = null;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
