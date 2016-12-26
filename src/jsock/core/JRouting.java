/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.core;

import jsock.message.JInMessages;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Routing tasks
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JRouting extends Thread{
    /**
     * size of task pool
     */
    private final int taskPoolSize;
    /**
     * socket message from users
     */
    private JInMessages message = null;
    /**
     * boolean flag routing is running
     */
    public static boolean isRunning = true;
    /**
     * Constructor
     * @param int taskPoolSize 
     */
    public JRouting(int taskPoolSize){
        this.taskPoolSize = taskPoolSize;
    }
    
    @Override
    public void run() {
        //executors pool
        ExecutorService executor   = Executors.newFixedThreadPool(taskPoolSize);

        while(JRouting.isRunning){
            //create pool of tasks
            for (String key : JInMessages.getKeySet()) {
                message     =  (JInMessages) JInMessages.get(key);
                
                Object task = loadTask(message);
                //if task exists
                if(task != null)
                    executor.execute((Runnable) task);
                
            }
        }
    }
    
    /**
     * Create task from variable JSocketMessages.task
     * and directory jsock.tasks.*
     * @param message
     * @return JTask(JSocketMessages)
     */
    private Object loadTask(JInMessages message){
        try {
            //task name for Json
            
            String className = "tasks." + message.json.get("task");
            Constructor    c = Class.forName(className).getConstructor(JInMessages.class);
            
            Object task      =  c.newInstance(message);
            
            return task;
        } catch (NoSuchMethodException  | SecurityException      | ClassNotFoundException   |
                InstantiationException  | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            
            //System.out.println(ex.getMessage());
            return null;
        }
    }
}
