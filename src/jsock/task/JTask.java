/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.task;

import org.json.simple.JSONObject;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JTask extends Thread{
        public JSONObject errors    = null;
        
        public JTask() {
            
        }
        
        @Override
        public void run() {
            beforeAction();
            if(errors == null){
                action();
                afterAction();
            }
        }
        
        public void action(){
        
        }
        
        public void beforeAction(){
           
        }
        
        public void afterAction(){
           
        }
}
