/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package tasks;

import jsock.message.JInMessages;
import jsock.message.JOutMessages;
import jsock.task.JClientTask;
import models.Users;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JTestTask extends JClientTask{

    public JTestTask(JInMessages message) {
        super(message);
    }
    
    @Override
    public String[][] rules(){
        String[][] rules = {
             {"require","message"}
        };
        return rules;
    }
    
    @Override
    public String rights() {
        //String rigths = "user,admin";
        String rigths = "guest";
        
        return rigths;
    }

    
    @Override
    public void action(){
        
       //System.out.println(webUser.email);
        //System.out.println("test");
       //String token = this.message.json.get("auth_token").toString();
       
       String message   = this.message.json.get("message").toString();

       System.out.println(message);
       
       String outString = "{\"message\":\"Test\"}";
       
       
       JOutMessages outMessage = new JOutMessages(this.message.ip,this.message.port,outString);
       outMessage.insert();
       
       //System.out.println(message);
       
      //     System.out.println(this.connection.auth_token + " " + this.message.json.get("token").toString());
      // }
       
       // System.out.println(this.connection.auth_token + " " + this.message.json.get("token").toString());

       // String outString = "{\"message\":\""+this.connection.auth_token+"\"}";
        
        //System.out.println();
        
        //System.out.println("token " + this.connection.auth_token);
        

        
        //System.out.println();
    }
    

}
