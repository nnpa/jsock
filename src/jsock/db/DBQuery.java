/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class DBQuery {
   public int id;
    
   public String tableName = "";
   
   public ResultSet rs = null;
    
   public ResultSet execute(String sql){
       try {
           DBConnection db = DBConnection.getInstance();

           rs = db.statement.executeQuery(sql);
           return rs;

       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
       return rs;
   }
   
   public void deleteById(int id){
       try {
           DBConnection db = DBConnection.getInstance();
           DBConnection.statement.executeUpdate("DELETE FROM `"+db.dbName+"`.`"+ tableName +"` where id = " + id);
       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public int delete(){
       deleteById(id);
       return id;
   }
   
   public void setTableName(String name){
       tableName = name;
   }
   
   public ResultSet findById(int id){
        DBConnection db = DBConnection.getInstance();

        String sql    = "SELECT * FROM `"+db.dbName+"`.`"+ tableName +"` where id = " + id;
        
        return execute(sql);
   }
   
   public ResultSet find(String condition){
        DBConnection db = DBConnection.getInstance();

        String sql    = "SELECT * FROM `"+db.dbName+"`.`"+ tableName +"` where id = " + condition;
        
        return execute(sql);
   }
   
   public void insert(String insert){        
       try {
           DBConnection db = DBConnection.getInstance();
           String sql      = "INSERT INTO `"+db.dbName+"`.`"+ tableName +"` " + insert;    
           DBConnection.statement.executeUpdate(sql);
       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public void update(String set,String condition){
       try {
           DBConnection db = DBConnection.getInstance();
           String sql      = "UPDATE `"+db.dbName+"`.`"+ tableName +"` " + set + " " + condition;
           System.out.println(sql);
           DBConnection.statement.executeUpdate(sql);
       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
