/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.task;

import org.json.simple.JSONObject;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JTask extends Thread{
       /**
        * Errors
        */
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
        /**
         * Main action
         */
        public void action(){
            
        }
        /**
         * Before action
         */
        public void beforeAction(){
           
        }
        /**
         * After Action
         */
        public void afterAction(){
           
        }
}
