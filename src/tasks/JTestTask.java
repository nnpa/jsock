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
       
       String outString = "{\"class\":\"windows.LoginDialog\",\"method\":\"test\"}";
       
       JOutMessages outMessage = new JOutMessages(this.message.ip,outString);
       outMessage.insert();
       
    }
    

}
