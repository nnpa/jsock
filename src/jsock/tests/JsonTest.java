/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JsonTest {
    
    public static void main(String[] args){
       JSONObject object = new JSONObject();
        object.put("name", "j4web");
        object.put("site", "http://j4web.ru");
        object.put("age", 2);
        JSONArray messages = new JSONArray();
        messages.add("Message 1");
        messages.add("Message 2");
        messages.add("Message 3");
        object.put("messages", messages);
        //System.out.print(object.toJSONString());  
    }
}
