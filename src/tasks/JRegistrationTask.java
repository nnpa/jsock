/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package tasks;


import jsock.core.JMailer;
import jsock.helpers.JEmailValidator;
import jsock.message.JInMessages;
import jsock.message.JOutMessages;
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
        String outString = "";
        
        String email    = this.message.json.get("email").toString();
        
        Users user = new Users();
        if(!user.exists(email) && JEmailValidator.isValidEmailAddress(email) ){
            String password = user.addUser(email);
            JMailer.sendMail("jetananas@yandex.ru","registration","password: " + password);
            outString = "{\"message\":\"User created, check mailbox\"}";

        }else{
            outString = "{\"error\":\"User exists or email not valid\"}";
        }
        JOutMessages outMessage = new JOutMessages(this.message.ip,outString);
        outMessage.insert();
        //System.out.println(email);
        
    }
}
