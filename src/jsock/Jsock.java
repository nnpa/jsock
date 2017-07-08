/*
 * jsock framework https://github.com/nnpa/jsock open source
 * 
 */
package jsock;

import java.util.logging.Level;
import java.util.logging.Logger;
import conf.JConfig;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import jsock.core.JCache;
import jsock.core.JCommandExecutor;
import jsock.core.JConnections;
import jsock.db.DBConnection;
import jsock.core.JTCPReciver;
import jsock.core.JRouting;
import jsock.core.JTCPSender;
import jsock.core.JUDPRecivier;
import jsock.core.JUDPSender;
import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.xml.sax.SAXException;

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
        
        /*
        * https://github.com/bitletorg/weupnp
        * https://en.wikipedia.org/wiki/Universal_Plug_and_Play
        */
        
        if(JConfig.upnp){
            Jsock.upnpPort(JConfig.client_port);
        }
        
        //Charset
        try {
            System.setProperty("file.encoding","UTF-8");
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null,null);
            
            //shutdown hook
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
            
            Jsock app = new Jsock();
            app.initCore();
            
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        //shutdown hook
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        
        Jsock app = new Jsock();
        app.initCore();
    }
    
    
    
    public void initCore(){
        JCache cache = JCache.getInstance();
        cache.runTimer();
        
        
        //asdasd
        //connection life time
        JConnections.life_time = JConfig.connection_life_time;
        
        //create tcp pool
        if(JConfig.protocol.equals("tcp")){
            JTCPReciver reciver = new  JTCPReciver(JConfig.receive_pool,JConfig.server_port);
            reciver.start();
            
            JTCPSender sender   = new  JTCPSender(JConfig.sender_pool);
            sender.start();
        }
        
        //create udp poll
        if(JConfig.protocol.equals("udp")){
            JUDPRecivier recivier = new  JUDPRecivier(JConfig.receive_pool,JConfig.server_port);
            recivier.start();
            
            JUDPSender sender     = new JUDPSender(JConfig.sender_pool);
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
                modules.put(moduleName, module);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void upnpPort(int port){
        try {
            GatewayDiscover discover = new GatewayDiscover();
            discover.discover();
            
            GatewayDevice d = discover.getValidGateway();
            
            if (null != d) {
                System.out.println(d.getLocation());
                
                //       new Object[]{d.getModelName(), d.getModelDescription()});
            } else {
                System.err.println("not discover");
            }
            
            InetAddress localAddress = d.getLocalAddress();
            
            String externalIPAddress = d.getExternalIPAddress();
            
            PortMappingEntry portMapping = new PortMappingEntry();
            
            if (d.getSpecificPortMappingEntry(port,"UDP",portMapping)) {
                System.out.println("upnpPort mapped");
            }
            
            /**
                if (d.addPortMapping(8086, 8086,localAddress.getHostAddress(),"UDP","test")) {
                    System.out.println("port add 8086");
                }   
            **/
            
            if (d.addPortMapping(port, port,localAddress.getHostAddress(),"UDP","test")) {
                System.out.println("port add " + port);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Jsock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
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
