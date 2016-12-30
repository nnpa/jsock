/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JsonTest {

    @Test
    public void jsonTest(){
       JSONObject object = new JSONObject();
        object.put("name", "j4web");
        object.put("site", "http://j4web.ru");
        object.put("age", 2);
        JSONArray messages = new JSONArray();
        messages.add("Message 1");
        messages.add("Message 2");
        messages.add("Message 3");
        object.put("messages", messages);
    }
}
