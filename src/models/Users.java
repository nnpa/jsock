/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package models;

import jsock.db.DBQuery;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class Users extends DBQuery{
    
    public Users() {
        setTableName("Users");
    }
    
    /*
    public void findById(String findId){
        String sql    = "SELECT * FROM jdbctest.Users where id = " + findId;
        
        ResultSet result   = execute(sql);
        
        try {
            while (result.next()) {
                name           = result.getNString("name");
                id             = result.getInt("id");
                session_key    = result.getNString("session_key");
                email          = result.getNString("email");
                password       = result.getNString("password");
                activation_key = result.getNString("activation_key");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    
}
