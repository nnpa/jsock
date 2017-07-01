/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package jsock.core;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * public static class GenericCache<K, V>
 * @author nn
 * @param <K>
 * @param <V>
 */
public class JCache<K, V> {
    
    public static JCache instance;

    public  Map<K, V> cache = new HashMap<K, V>();  

    public static HashMap<String,Integer> expiries = new HashMap<String,Integer>();

    public static int checkTime = 30000;
    
    public static java.util.Timer timer;
    
    public JCache(){
        
    }
    
    public  void set(K key, V value,int time){  
        
        instance.cache.put(key, value);  
        
        long currentTime = System.currentTimeMillis();
        
        int lifeTime     =   (int) (currentTime + time);
        
        instance.expiries.put((String) key, lifeTime);

    }  
  
     //Generic method  
     public  V get(K key){  
         return cache.get(key);  
     }
     
     /**
     * 
     * @return 
     */
    public static synchronized JCache getInstance() {
        if (instance == null) {
            instance = new JCache();
        }
        return instance;
    }
    
    public static void runTimer(){
        
        instance.timer   =   new java.util.Timer();
        
        instance.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String key : instance.expiries.keySet()) {
                    int time         = instance.expiries.get(key);
                    long currentTime = System.currentTimeMillis();

                    if(time < currentTime){
                        instance.expiries.remove(key);
                        instance.remove(key);
                    }
                }
            }
        },instance.checkTime);
    }
    
    /**
     * remove cache
     * @param key 
     */
    public  void remove(K key){
        instance.cache.remove(key);
        instance.expiries.remove(key);
    }

}
