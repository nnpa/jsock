/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package jsock.core;

import conf.JConfig;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author nn
 */
public class JGarbageCollector extends Thread{
    private int timeOut;
    
    private String [] call;
    
    public static boolean stopFlag = false;
    /**
     * 
     * @param timeOut
     * @param callClass
     */
    public JGarbageCollector(int timeOut,String[] callClass){
        this.timeOut = timeOut;
        this.call    = callClass;
        
    }
    
    @Override
    public void run() {
        while(!JGarbageCollector.stopFlag){
            //Class myClass = Class.forName("Demo");
            
            Class  callClass;
            
            for(String className : this.call){
                try {
                    callClass = Class.forName(className);
                    Method m  = callClass.getDeclaredMethod("clear", null);
                    Object o  = m.invoke(null,  null);
                    
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(JGarbageCollector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                Thread.sleep(this.timeOut);
            } catch (InterruptedException ex) {
                Logger.getLogger(JGarbageCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
