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
 * Outout messages
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JOutMessages{
    /**
     * Message from
     */
    public String ip;
    /**
     * Data read or send to socket
     */
    public JSONObject json;
    
    /**
     * Message create time
     */
    public long   time;
    
    private static final  Map<String, JOutMessages> list = new ConcurrentHashMap<>();
    /**
     * Create JOutMessages object from socket and JSONObject
     * @param ip 
     * @param json 
     */
    public JOutMessages(String ip,JSONObject json){
            this.ip      = ip;
            this.json    = json;
            this.time    = System.currentTimeMillis();
    }
    /**
     * @param ip
     * @param data 
     */
    public JOutMessages(String ip,String data){
        try {
            this.ip      = ip;
            this.time    = System.currentTimeMillis();
            //String data
            
            JSONParser parser = new JSONParser();
            
            Object obj = parser.parse(data);
            json       = (JSONObject) obj;
            
        } catch (ParseException ex) {
            Logger.getLogger(JOutMessages.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    /**
     * Insert JOutMessages object into hash map
     */
    public synchronized void insert(){

        String key = genKey();

        if(JOutMessages.list.containsKey(key))
            JOutMessages.list.remove(key);

        JOutMessages.list.put(key, this);

    }
    
     /**
     * Get data by key
     * @param key
     * @return 
     */
    public static synchronized JOutMessages get(String key){
        
        JOutMessages message = null;

        if(JOutMessages.list.containsKey(key))
            message = JOutMessages.list.get(key);

        if(JOutMessages.list.containsKey(key))
            JOutMessages.list.remove(key);
        
        return message;
    }
    
    public static synchronized  Set<String> getKeySet(){                
        return JOutMessages.list.keySet();
    }
    
    /**
     * generate key for hash map
     * @return 
     */
    public String genKey(){
        return this.ip + "_" + this.time;
    }
}
