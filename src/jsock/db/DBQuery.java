/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database queries 
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class DBQuery {
   /**
    * Primary key
    */
   public int id;
   /**
    * Table name
    */ 
   public String tableName = "";
   /**
    * Result set
    */
   public ResultSet rs = null;
   /**
    * DBConnection 
    */
   public DBConnection db;

   public DBQuery(){
     db = DBConnection.getInstance();

   }
   /**
    * 
    * @param sql
    * @return 
    */
   public ResultSet execute(String sql){
       try {
           rs = db.statement.executeQuery(sql);
           return rs;

       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
       return rs;
   }
   /**
    * Delete row by id
    * @param id 
    */
   public void deleteById(int id){
       try {
           DBConnection.statement.executeUpdate("DELETE FROM `"+db.dbName+"`.`"+ tableName +"` where id = " + id);
       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   /**
    * Delete row by id
    * @return 
    */
   public int delete(){
       deleteById(id);
       return id;
   }
   /**
    * Set table name
    * @param name 
    */
   public void setTableName(String name){
       tableName = name;
   }
   /**
    * Find element by id
    * @param id
    * @return 
    */
   public ResultSet findById(int id){

        String sql    = "SELECT * FROM `"+db.dbName+"`.`"+ tableName +"` where id = " + id;
        
        return execute(sql);
   }
   /**
    * Find element by id - whre condition
    * Example find("id=1")
    * @param condition
    * @return 
    */
   public ResultSet find(String condition){
        
        String sql    = "SELECT * FROM `"+db.dbName+"`.`"+ tableName +"` where " + condition;
        
        return execute(sql);
   }
   /**
    * Insert statement 
    * Example insert("(`id`) VALUES (1)")
    * @param insert 
    */
   public void insert(String insert){        
       try {
           String sql      = "INSERT INTO `"+db.dbName+"`.`"+ tableName +"` " + insert;    
           DBConnection.statement.executeUpdate(sql);
       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   /**
    * Update query
    * @param set
    * @param condition 
    */
   public void update(String set,String condition){
       try {
           String escapeString = condition;
           
           
           String sql      = "UPDATE `"+db.dbName+"`.`"+ tableName +"` " + set + " " + escapeString;
           
           System.out.println(sql);
           
           DBConnection.statement.executeUpdate(sql);
       } catch (SQLException ex) {
           Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   /**
    * Escape mysql string
    * @param s
    * @return 
    */
   public static String unescapeMySQLString(String s){
          // note: the same buffer is used for both reading and writing
          // it works because the writer can never outrun the reader
          char chars[] = s.toCharArray();

          // the string must be quoted 'like this' or "like this"
          if (chars.length < 2 || chars[0] != chars[chars.length-1] ||
              (chars[0] != '\'' && chars[0] != '"')) {
            throw new IllegalArgumentException("not a valid MySQL string: " + s);
          }

          // parse the string and decode the backslash sequences; in addition,
          // quotes can be escaped 'like this: ''', "like this: """, or 'like this: "'
          int j = 1;  // write position in the string (never exceeds read position)
          int f = 0;  // state: 0 (normal), 1 (backslash), 2 (quote)
          for (int i = 1; i < chars.length - 1; i++) {
              switch (f) {
                  case 0:
                      // previous character was normal
                      if (chars[i] == '\\') {
                          f = 1;  // backslash
                      } else if (chars[i] == chars[0]) {
                          f = 2;  // quoting character
                      } else {
                          chars[j++] = chars[i];
                      }             break;
                  case 1:
                      // previous character was a backslash
                      switch (chars[i]) {
                          case '0':   chars[j++] = '\0';   break;
                          case '\'':  chars[j++] = '\'';   break;
                          case '"':   chars[j++] = '"';    break;
                          case 'b':   chars[j++] = '\b';   break;
                          case 'n':   chars[j++] = '\n';   break;
                          case 'r':   chars[j++] = '\r';   break;
                          case 't':   chars[j++] = '\t';   break;
                          case 'z':   chars[j++] = '\032'; break;
                          case '\\':  chars[j++] = '\\';   break;
                          default:
                              // if the character is not special, backslash disappears
                              chars[j++] = chars[i];
                              break;
                      }             f = 0;
                      break;
                  default:
                      // previous character was a quote
                      // quoting characters must be doubled inside a string
                      if (chars[i] != chars[0]) {
                          throw new IllegalArgumentException("not a valid MySQL string: " + s);
                      }             chars[j++] = chars[0];
                      f = 0;
                      break;
              }
          }
          // string contents cannot end with a special character
          if (f != 0) {
            throw new IllegalArgumentException("not a valid MySQL string: " + s);
          }

          // done
          return new String(chars, 1, j - 1);
    }
}
