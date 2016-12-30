/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package tests;

import jsock.message.JOutMessages;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class OutMessageTest {


    @Test
    public void outMessageTest() {
        String ip = "asdasd";
        JSONObject json = new JSONObject();
        JOutMessages out = new JOutMessages(ip, json);
        out.insert();
        String key = out.ip + "_" + out.time;
        Assert.assertEquals(key, JOutMessages.get(key).genKey());
    }
}

