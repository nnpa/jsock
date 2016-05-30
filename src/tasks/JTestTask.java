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
             {"require","user_id,session_key"},
             {"rights","guest"}
        };
        return rules;
    }
    
    @Override
    public void action(){
        Users users = new Users();
        //users.deleteById(2);
        users.insert("(`name`,`auth_key`,`email`,`password`,`activation_key`)VALUES (\"Boris\",\"asdasd\",\"sgongaz@yandex.ru\",\"Aksjj24\",\"Jaksmek8\");");
         //users.findById("2");
        //System.out.println(users.email);
        //users.update(" SET  `name` = \"test\""," where id = 3");
        
        String outString = "{\"message\":\"test task complite\"}";
        JOutMessages outMessage = new JOutMessages(this.message.ip,outString);
        outMessage.insert();
    }
    
}
