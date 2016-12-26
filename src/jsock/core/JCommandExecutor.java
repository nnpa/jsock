/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package jsock.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Run user commands fom directory commands
 * @author nn
 */
public class JCommandExecutor extends Thread{
    /**
     * Each commands timeout
     */
    private int timeOut;
    /**
     * List of commands
     */
    public static List<String>  call = new ArrayList<String>();
    /**
     * run/stop flag
     */
    public static boolean stopFlag = false;
    /**
     * 
     * @param int timeOut
     * @param String[] callClass
     */
    public JCommandExecutor(int timeOut,String[] callClass){
        this.timeOut             = timeOut;
        
        JCommandExecutor.call    =  new ArrayList<>(Arrays.asList(callClass));
        
    }
    
    @Override
    public void run() {
        while(!JCommandExecutor.stopFlag){
            //Class myClass = Class.forName("Demo");
            
            Class  callClass;
            
            for(String className : JCommandExecutor.call){
                try {
                    callClass = Class.forName("commands."+className);
                    Method m  = callClass.getDeclaredMethod("run", null);
                    Object o  = m.invoke(null,  null);
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(JCommandExecutor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
            try {
                Thread.sleep(this.timeOut);
            } catch (InterruptedException ex) {
                Logger.getLogger(JCommandExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
