/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsock.core.JConnections;
import jsock.db.DBConnection;
import jsock.db.DBQuery;
import jsock.helpers.JRandomString;

/**
 * 
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class Users extends DBQuery{
    public static String salt = "a#4H5";
    
    public String email;
    
    public String password;
    
    public String activation_key;

    public String rights = "guest";
    
    public int  create_time;
    
    public Users() {
        setTableName("users");
    }
    
    public void byId(int findId){
        
        ResultSet result   = findById(findId);
        
        try {
            while (result.next()) {
                loadFields(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * 
     * @param email
     * @param password
     * @return token
     */
    public boolean authorization(String email,String password){
        ResultSet result = find("email = '" + email + "' AND `password` = '" + password + "'");
        
        try {
            if (result.next()){
                
                loadFields(result);
                
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void loadFields(ResultSet result){
        try {
            id              = result.getInt("id");
            email           = result.getNString("email");
            password        = result.getNString("password");
            rights          = result.getNString("rights");
            create_time     = result.getInt("create_time");
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean exists(String email){
        
        try {
            String countQuery = "SELECT COUNT(*) AS count FROM `"+DBConnection.dbName+"`.`"+ tableName +"` where email = '" + email + "'";
            
            
            ResultSet result = DBConnection.statement.executeQuery(countQuery);
            int count = 0;
            
            while (result.next()) {
                count = result.getInt("count");
                
            }
            
            if(count > 0)
               return true;
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
    
    public  synchronized String getToken(){
        String tokenString = email + " " + password;

        String token = md5(tokenString) + (System.currentTimeMillis() / 1000L);
        return token;
    }
    
    public  synchronized String md5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            
            byte byteData[] = md.digest();
            
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            return  sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(JConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String addUser(String email){
        String password    = JRandomString.generateRandom(8);
        String query = " (`email`,`password`,`rights`,`create_time`)VALUES ('"+email+"','"+ password +"','user',UNIX_TIMESTAMP(now()));";

        insert(query);
        
        return password;
    }
    
    
}
