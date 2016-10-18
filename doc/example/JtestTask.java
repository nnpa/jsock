/*
 * jsock framework https://github.com/Padaboo/jsock open source
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
    public void action(){

       String message   = this.message.json.get("message").toString();

       String outString = "{\"message\":\""+message+"\"}";
       
       JOutMessages outMessage = new JOutMessages(this.message.ip,outString);
       outMessage.insert();
     
    }
    
    @Override
    public String rights() {
        //String rigths = "user,admin";
        String rigths = "guest";
        
        return rigths;
    }
}

