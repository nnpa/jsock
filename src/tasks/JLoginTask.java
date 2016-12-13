/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package tasks;

import jsock.message.JInMessages;
import jsock.message.JOutMessages;
import jsock.task.JClientTask;
import models.Session;
import models.Users;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JLoginTask extends JClientTask{

    public JLoginTask(JInMessages message) {
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
        
        JOutMessages outMessage = new JOutMessages(this.message.ip,outString);
        outMessage.insert();
        
        //System.out.println(this.message.json.get("email"));
        
        //System.out.println(this.message.json.get("password"));
        
        
        //email = 'jetananas@yandex.ru' and `password` = 'adsasda';

        //if(){
       //     
       // }
        /**
        Users users = new Users();
        //users.deleteById(2);
        users.insert("(`name`,`email`,`password`,`activation_key`,`rights`)VALUES (\"Boris\",\"sgongaz@yandex.ru\",\"Aksjj24\",\"Jaksmek8\",\"user\");");
         //users.findById("2");
        //System.out.println(users.email);
        //users.update(" SET  `name` = \"test\""," where id = 3");
        //System.out.println(this.message.json.toString());
        
        String outString = "{\"message\":\"test task complite\"}";
        JOutMessages outMessage = new JOutMessages(this.message.ip,outString);
        outMessage.insert();
        * */
    }
    
}
