/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * 
 */
package jsock;

import java.util.logging.Level;
import java.util.logging.Logger;
import conf.JConfig;
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
    
    //private String  rootPath;
    
    //private String 
    //private net secyry;

    //private Templates;

    //private User;

    //private Memory;

    //private socketThreadQueue tcp/udp online;

    //private Controllers;

    //private Routing;

    //private Dao/AR;

    //private Singnals;
    
    //private Options;
    
    /**
     * List of load modules
     */
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Jsock app = new Jsock();
        app.initCore();
    }
    

    public void initCore(){
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
        
        JRouting taskRouter = new JRouting(JConfig.task_pool);
        
        taskRouter.start();
        
        DBConnection.getInstance();

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
                moduleClass = Class.forName( "jsock.modules." + moduleName );
                module      = moduleClass.newInstance();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
