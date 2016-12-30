/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.task;

import jsock.core.JConnections;
import jsock.message.JInMessages;
import jsock.message.JOutMessages;
import jsock.validators.JRequestValidator;
import models.Session;
import models.Users;
import org.json.simple.JSONObject;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public abstract class JClientTask extends JTask{
    /**
     * JMessages object
     */
    public final JInMessages message;
    /**
     * Connections
     */
    public JConnections connection;
    /**
     *  WebUser
     */
    public Users webUser;

    
    /**
     * Errors list
     * @param message
     */
    public JClientTask(JInMessages message) {
        this.message    = message;
        
        this.connection = new JConnections(this.message.ip);
        this.connection = this.connection.get();
    }
    //Task action
    @Override
    public void action(){
        
        //
        
    }
    /**
     * Client messag and other rules
     * @return String[][] rules
     */
    public abstract String[][] rules();
    
    /**
     * User permissions 
     * @return String[][] rights
     */
    public abstract String rights();
    
    //validate 
    public void validate(){
        String[][] rules = rules();

        JRequestValidator rv = new JRequestValidator(message.json,rules);
        errors               = rv.check();
        
        if(errors != null){
            JOutMessages outMessage = new JOutMessages(this.message.ip,errors);
            outMessage.insert();
        }
    }
    /**
     * Before action
     */
    @Override
    public void beforeAction(){
        validate();
        //check rights and load user
        if(!rights().equals("guest")){
            loadUser();
            checkRights();
        }
    }
    /**
     * Load user class
     */
    public void loadUser(){
        
        String token    = message.json.get("auth_token").toString();
        
        Session session = new Session();
        session.findByToken(token);

        Users user = new Users();
        user.findById(session.user_id);
        
        webUser = user;
    }
    /**
     * Check rights and permissions
     * @return 
     */
    public boolean checkRights(){
        String rights  = rights();
        String[] parts = rights.split(",");
        
        for (String right: parts) {
            //System.out.println("rights " + right + " " + webUser.rights);
            
            if(right.equals(webUser.rights)){
                
                
                return true;
            }
        }
        
        errors = new JSONObject();
        
        String message = "{\"error\":\"Not have permission\"}";

        JOutMessages outMessage = new JOutMessages(this.message.ip,message);
        outMessage.insert();
        
        return false;
    }
    
}
