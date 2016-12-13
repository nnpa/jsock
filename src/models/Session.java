/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import jsock.db.DBConnection;
import jsock.db.DBQuery;

/**
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class Session extends DBQuery{

    public static Session getInstance(Properties props, Authenticator authenticator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * User id
     */
    public int    user_id;
    /**
     * Token
     */
    public String token;
    /**
     * Create time
     */
    public int    time;
   
    //300 second by default for framework test
    public static int sessionLifeTime = 300;
    
    public Session() {
        setTableName("session");
    }
    /**
     * Find row by token
     * @param token 
     */
    public void findByToken(String token){
        ResultSet result = find("token = '" + token + "'");
        
         try {
            while (result.next()) {
                loadFields(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Load fields
     * @param result 
     */
    public void loadFields(ResultSet result){
        try {
            user_id         = result.getInt("user_id");
            token           = result.getNString("token");
            time            = result.getInt("time");
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Set token
     * @param userId
     * @param token
     * @param ip 
     */
    public void setToken(int userId,String token,String ip){
        try {
            int time           = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();

            
            String countQuery = "SELECT COUNT(*) AS count FROM `"+db.dbName+"`.`"+ tableName +"` where user_id = '" + userId + "'";
            
            
            ResultSet result = db.statement.executeQuery(countQuery);
            int count = 0;
            
             while (result.next()) {
                count = result.getInt("count");

             }

            if(count > 0){
                String update = " set `user_id` = \"" + userId + "\", `token` = \"" + token + "\", `time` = UNIX_TIMESTAMP(now()), `ip` = \"" + ip +"\"";

                update(update," where user_id = " + userId);
                //UNIX_TIMESTAMP(now())
            } else {
                String insertQuery = " (`user_id`,`token`,`time`,`ip`)VALUES (" + userId +",\""+ token +"\",UNIX_TIMESTAMP(now()),\""+ ip +"\");";
                
                insert(insertQuery);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /**
     * Delete old session rows by session life time
     * 
     */
    public void clearSession(){
        try {
            String query = "DELETE FROM `"+db.dbName+"`.`"+ tableName +"` where (`session`.`time` + " + sessionLifeTime +") < UNIX_TIMESTAMP(now());";
            DBConnection.statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}