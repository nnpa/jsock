/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package commands;

import jsock.core.JConnections;
import models.Session;

/**
 * Test user command
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JSystemGarbage {
    //restart time / run time out, run command every 20 second's
    public static int timeout       = 20000;
    //last time when command run (seconds
    public static long lastRunTime  = 0;
    //stop flag
    public static boolean stopFlag  = false;
    
    
    //any custom user parameters
    public JSystemGarbage() {
        
    }
    
    public static void run(){
       if(condition()){
         //delete old sessions by life time
         Session session = new Session();
         session.clearSession();
         //delete old connection
         JConnections.clear();
       }
    }
    /**
     * run condition   
     * run command every 20 seconds by example
     * @return 
     */
    public static boolean condition(){
        boolean flag = false;
        //run every 20 seconds
        long currentTime = System.currentTimeMillis();
        
        if((lastRunTime + timeout) < currentTime){
            
            lastRunTime = currentTime;
            flag = true;
        }
        // u can add user condition here, for example:
        // if(1=1){ flag = false;}
        
        return flag;
    }
    
    //public static get
}
