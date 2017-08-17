/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.message;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Input messages
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JInMessages{
    /**
     * Message from
     */
    public String ip;
    /**
     * Data read or send to socket
     */
    public JSONObject json = null;
    
    /**
     * Message create time
     */
    public long   time;
    
    public String port;
    
    public static long number = 0;

    
    private static final  Map<String, JInMessages> list = new ConcurrentHashMap<>();
    /**
     * Create JInMessages object from socket and socket data
     * @param ip
     * @param json 
     */
    public JInMessages(String ip,JSONObject json){
            this.ip      = ip;
            this.json    = json;
            this.time    = System.currentTimeMillis();
            this.port    = Long.toString((long) json.get("client_port"));
    }
    
    public JInMessages(String ip,String data){
        try {
            this.ip      = ip;
            this.time    = System.currentTimeMillis();
            //String data
            
            JSONParser parser = new JSONParser();
            
            Object obj = parser.parse(data);

            json       = (JSONObject) obj;
            
            port       =  Long.toString((long) json.get("client_port"));

        } catch (ParseException ex) {
            
            Logger.getLogger(JInMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Insert JInMessages object into hash map
     */
    public synchronized void insert(){
        
        String key = genKey();
    
        if(JInMessages.list.containsKey(key))
            JInMessages.list.remove(key);
        
        if(this.json != null)
            JInMessages.list.put(key, this);
        
    }
    
     /**
     * Get data by key
     * @param key
     * @return 
     */
    public static synchronized JInMessages get(String key){
       
       JInMessages message = null;

       if(JInMessages.list.containsKey(key))
           message = JInMessages.list.get(key);
       
       if(JInMessages.list.containsKey(key))
          JInMessages.list.remove(key);
              
       return message;
    }
    
    public static synchronized  Set<String> getKeySet(){                  
        return JInMessages.list.keySet();
    }
    
    /**
     * generate key for hash map
     * @return 
     */
    public String genKey(){
        number ++;
        return this.ip + "_" + this.time + "_" + number;

    }
    /**
     * get variable from JSONObject by key
     * @param key 
     * @return Object cast (JSONObject)
     */
    public Object getJson(String key){
        return json.get(key);
    }
}
