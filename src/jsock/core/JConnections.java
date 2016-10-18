/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.core;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * List of user connections and connection object
 * user ip - key of hash map
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JConnections {


    /**
     * ip connection
     */
    public  String   ip;
    /**
     * port connection
     */
    public  short   port;
    /**
     * the last query time
     */
    public  long updateTime;
    /**
     * connection life time
     */
    public static  int LIFE_TIME = 500;
    
    /**
     *  Hash map (list) of actual connections
     */
    private static final Map<String, JConnections> map = new ConcurrentHashMap<>();
    
    
    public JConnections(String ip){
        this.ip = ip;
        //this.ip         = socket.getInetAddress().toString();
        this.updateTime = System.currentTimeMillis();
    }
    
    /**
     * Get client info object by key
     * @return 
     */
    public synchronized JConnections get(){
        if(JConnections.map.containsKey(this.ip))
            return JConnections.map.get(this.ip);
        
        return null;
    }
    
    /**
     * Remove connection o by key 
     */
    public synchronized void remove(){
        if(JConnections.map.containsKey(this.ip))
            JConnections.map.remove(this.ip);
    }
    
    /**
     * Remove old connections by life time
     * by lifetime
     */
    public static synchronized void clear(){
        
        for(Entry<String, JConnections> e : JConnections.map.entrySet()) {
            JConnections connection = (JConnections) e.getValue();
            if((connection.updateTime + JConnections.LIFE_TIME) > System.currentTimeMillis()){
                JConnections.map.remove(e.getKey());
            }
        }
        
    }
    
    /**
     * Store connection to list
     */
    public synchronized void  insert(){
        if(JConnections.map.containsKey(this.ip))
            JConnections.map.remove(this.ip);
        
        JConnections.map.put(this.ip, this);
    }
}
