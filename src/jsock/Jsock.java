/*
 * jsock framework https://github.com/nnpa/jsock open source
 * 
 */
package jsock;

import java.util.logging.Level;
import java.util.logging.Logger;
import conf.JConfig;
import java.util.HashMap;
import java.util.Map;
import jsock.core.JCache;
import jsock.core.JCommandExecutor;
import jsock.core.JConnections;
import jsock.db.DBConnection;
import jsock.core.JTCPReciver;
import jsock.core.JRouting;
import jsock.core.JTCPSender;
import jsock.core.JUDPRecivier;
import jsock.core.JUDPSender;

/**
 * Main run file
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class Jsock {

    public static Map<String, Object> modules = new HashMap<String, Object>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //shutdown hook
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        
        Jsock app = new Jsock();
        app.initCore();
    }
    
    public void initCore(){
        
        JCache cache = JCache.getInstance();
        cache.runTimer();
        
        
        
        //connection life time
        JConnections.life_time = JConfig.connection_life_time;
        
        //create tcp pool
        if(JConfig.protocol.equals("tcp")){
            JTCPReciver reciver = new  JTCPReciver(JConfig.receive_pool,JConfig.server_port);
            reciver.start();
            
            JTCPSender sender   = new  JTCPSender(JConfig.sender_pool,JConfig.client_port);
            sender.start();
        }
        
        //create udp poll
        if(JConfig.protocol.equals("udp")){
            JUDPRecivier recivier = new  JUDPRecivier(JConfig.receive_pool,JConfig.server_port);
            recivier.start();
            
            JUDPSender sender     = new JUDPSender(JConfig.sender_pool,JConfig.client_port);
            sender.start();
        }
        //Routing user tasks
        JRouting taskRouter = new JRouting(JConfig.task_pool);
        taskRouter.start();
        
        //
        //Execute system and user commands
        JCommandExecutor gc = new JCommandExecutor(JConfig.executor_timeout,JConfig.executor_commands);
        gc.start();
        //db instance 
        DBConnection.getInstance();
        //init modules
        initModules();
    }
    
    /**
     * Read modules from JConfig create instance
     * and put to moduleLsit
     */
    public void initModules(){
        try {
            Object module;
            Class  moduleClass;
            for(String moduleName : JConfig.modules){
                moduleClass = Class.forName( "modules." + moduleName );
                module      = moduleClass.newInstance();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

/**
 * Shutdownhook
 * @author nn
 */
class ShutdownHook extends Thread {
    @Override
    public void run() {
        System.out.println("System halt");
    }
}
