/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.tests;

import jsock.message.JOutMessages;
import org.json.simple.JSONObject;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class OutMessageTest {
    public static void main(String[] args){
        String     ip    = "asdasd";
        JSONObject json  = new JSONObject();
        
        JOutMessages out = new JOutMessages(ip,json);
        out.insert();
        
        String key = out.ip +"_" + out.time;
        
        System.out.println(out);

        System.out.println(JOutMessages.get(key));
    }
}

