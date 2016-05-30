/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.task;

import jsock.message.JInMessages;
import jsock.message.JOutMessages;
import jsock.validators.JRequestValidator;
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
     * Task call errors 
     */
        
    /**
     * Errors list
     * @param message
     */
    public JClientTask(JInMessages message) {
        this.message = message;
    }
    
    @Override
    public void action(){
        
        //System.out.println("message " + message.json.toJSONString());
    }
    /**
     * 
     * @return String[][] rules
     */
    public abstract String[][] rules();
    
    public void filters(){
        
    }
    
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
    
    @Override
    public void beforeAction(){
        validate();
    }

}
