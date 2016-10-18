/*
 * jsock framework https://github.com/nnpa/jsock open source
 * 
 */
package jsock;

import java.util.logging.Level;
import java.util.logging.Logger;
import conf.JConfig;
import jsock.core.JGarbageCollector;
import jsock.core.JConnections;
import jsock.db.DBConnection;
import jsock.core.JTCPReciver;
import jsock.core.JRouting;
import jsock.core.JTCPSender;
import jsock.core.JUDPRecivier;
import jsock.core.JUDPSender;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class Jsock {
    //private User;

    //private Memory;

    //private Singnals;
    
    /**
     * List of load modules
     */
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        
        Jsock app = new Jsock();
        app.initCore();
    }
    
    public void initCore(){

        //connection life time
        JConnections.LIFE_TIME = JConfig.connection_life_time;
        
        //tpc or udp protocol
        if(JConfig.protocol.equals("tcp")){
            JTCPReciver reciver = new  JTCPReciver(JConfig.resiver_pool,JConfig.server_port);
            reciver.start();
            
            JTCPSender sender   = new  JTCPSender(JConfig.sender_pool,JConfig.client_port);
            sender.start();
        }
        
        if(JConfig.protocol.equals("udp")){
            JUDPRecivier recivier = new  JUDPRecivier(JConfig.resiver_pool,JConfig.server_port);
            recivier.start();
            
            JUDPSender sender     = new JUDPSender(JConfig.sender_pool,JConfig.client_port);
            sender.start();
        }
        //Routin
        JRouting taskRouter = new JRouting(JConfig.task_pool);
        
        taskRouter.start();
        //garabge collector

        JGarbageCollector gc = new JGarbageCollector(JConfig.garbage_timeout,new String[]{"jsock.core.JConnections"});
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
    public void run() {
        System.out.println("System halt");
    }
}
