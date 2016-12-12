/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package tasks;

import jsock.db.DBConnection;
import jsock.message.JInMessages;
import jsock.task.JClientTask;
import models.Session;
import models.Users;

/**
 *
 * @author nn
 */
public class LoginTask extends JClientTask{

    public LoginTask(JInMessages message) {
        super(message);
    }

    @Override
    public String[][] rules(){
        String[][] rules = {
             {"require","email,password"}
        };
        return rules;
    }
    
    @Override
    public String rights() {
        String rigths = "guest";
        
        return rigths;
    }
    
    @Override
    public void action(){
        
        String email    = this.message.json.get("email").toString();
        
        String password = this.message.json.get("password").toString();
        
        Users users     = new Users();
        
        boolean isAuth  = users.authorization(email,password);
        
        String outString;
        
        if(isAuth){
             
             String token       = users.getToken();
             int   userId       = users.id;
             
             String ip          = this.connection.ip;
             
             //System.out.println(insertQuery);
             
             Session session = new Session();
             
             session.setToken(userId, token,ip);
             
             //this.connection.setUserId(users.id);
             
             outString = "{\"token\":\"" + token +"\"}";
             //outString          = "{\"token\":\"" + this.connection.auth_token +"\"}";
             
             //this.connection
        }else{
             outString = "{\"error\":\"Incorrect login or password \"}";
        }
        
    }
}
