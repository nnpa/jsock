/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package tasks;

import jsock.message.JInMessages;
import jsock.task.JClientTask;
import models.Session;
import models.Users;

/**
 *
 * @author nn
 */
public class JRegistrationTask extends JClientTask{
    

    public JRegistrationTask(JInMessages message) {
        super(message);
    }
    
    @Override
    public String[][] rules(){
        String[][] rules = {
             {"require","email"}
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
        
        
    }
}
