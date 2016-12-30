/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package models;

import jsock.core.JHelpers;
import jsock.db.DBConnection;
import jsock.db.DBQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class Users extends DBQuery {
    /**
     * Salt string for hashing password
     */
    public static String salt = "salt&3_84";

    public String email;

    public String password;

    public String activation_key;

    public String rights = "guest";

    public int create_time;

    public Users() {
        setTableName("users");
    }

    /**
     * Authorization method return true or false and load user
     *
     * @param email    String
     * @param password String
     * @return boolean
     */
    public boolean authorization(String email, String password) {
        ResultSet result = find("email = '" + email + "' AND `password` = '" + hashPassword(password) + "'");

        try {
            if (result.next()) {

                loadFields(result);

                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Load fields
     *
     * @param result ResultSet
     */
    public void loadFields(ResultSet result) {
        try {
            id = result.getInt("id");
            email = result.getNString("email");
            password = result.getNString("password");
            rights = result.getNString("rights");
            create_time = result.getInt("create_time");
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * User  exists
     *
     * @param email String
     * @return boolean
     */
    public boolean exists(String email) {

        try {
            String countQuery = "SELECT COUNT(*) AS count FROM `" + DBConnection.dbName + "`.`" + tableName + "` where email = '" + email + "'";


            ResultSet result = DBConnection.statement.executeQuery(countQuery);
            int count = 0;

            while (result.next()) {
                count = result.getInt("count");

            }

            if (count > 0)
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    /**
     * Generate user token
     *
     * @return String
     */
    public synchronized String getToken() {
        String tokenString = email + " " + password;

        String token = JHelpers.md5(tokenString) + (System.currentTimeMillis() / 1000L);
        return token;
    }

    /**
     * Hash password
     *
     * @param str
     * @return md5 hashed password
     */
    public static synchronized String hashPassword(String str) {
        return JHelpers.md5(salt + str);
    }

    /**
     * Add user to database by email
     *
     * @param email
     * @return string password
     */
    public String addUser(String email) {
        String password = hashPassword(JHelpers.generateRandom(8));
        String query = " (`email`,`password`,`rights`,`create_time`)VALUES ('" + email + "','" + password + "','user',UNIX_TIMESTAMP(now()));";

        insert(query);

        return password;
    }

}
