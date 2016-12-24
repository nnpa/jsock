/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package tasks;

import jsock.core.JConnections;
import jsock.message.JInMessages;
import jsock.message.JOutMessages;
import jsock.task.JClientTask;
import models.Session;
import models.Users;

/**
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 * @author nn
 */
public class JRightsTask extends JClientTask{

    
    public JRightsTask(JInMessages message) {
        super(message);
    }
    
        
    @Override
    public String[][] rules(){
        String[][] rules = {
             {"require","auth_token,message"}
        };
        return rules;
    }
    
    @Override
    public String rights() {
        String rigths = "user";
        
        return rigths;
    }
    
    @Override
    public void action(){
        /**
         * get ip from session and send to open socket
        
        **/
        
        
        Session session = new Session();
        session.findByUserID(12);
        
        String ip = session.ip;
        
        System.out.println("ip " + ip);
        
        String outString = "{\"ip_message\":\"send by id\"}";
        
        JOutMessages outMessage = new JOutMessages(ip,outString);
        outMessage.insert();
        
        
        //System.out.println("asdasd");
        //or you can search online users in JConnections by ip
        //or you can searcg authorized users in Session model 
        
        
    }
}
