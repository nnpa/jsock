/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsock.db.DBQuery;

/**
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class Session extends DBQuery{
    public int    user_id;
    public String token;
    public int    time;
            
    public Session() {
        setTableName("session");
    }
    
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
    
    public void loadFields(ResultSet result){
        try {
            user_id         = result.getInt("user_id");
            token           = result.getNString("token");
            time            = result.getInt("time");
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setToken(int userId,String token,String ip){
        try {
            int time           = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();

            //ResultSet rs3 = ddbexecuteQuery("SELECT COUNT(*) AS count FROM `session` ;");
            
            String countQuery = "SELECT COUNT(*) AS count FROM `"+db.dbName+"`.`"+ tableName +"` where user_id = '" + userId + "'";
            
            //System.out.println(countQuery);
            
            ResultSet result = db.statement.executeQuery(countQuery);
            int count = 0;
            
             while (result.next()) {
                count = result.getInt("count");

             }

            //System.out.println(count);
            
            if(count > 0){
                String update = " set `user_id` = \"" + userId + "\", `token` = \"" + token + "\", `time` = \"" + time + "\", `ip` = \"" + ip +"\"";

                update(update," where user_id = " + userId);
            } else {
                String insertQuery = " (`user_id`,`token`,`time`,`ip`)VALUES (" + userId +",\""+ token +"\","+time+",\""+ ip +"\");";
                
                insert(insertQuery);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

}
