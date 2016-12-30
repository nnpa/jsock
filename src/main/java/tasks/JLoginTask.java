/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package tasks;

import jsock.message.JInMessages;
import jsock.message.JOutMessages;
import jsock.task.JClientTask;
import models.Session;
import models.Users;

/**
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JLoginTask extends JClientTask {

    public JLoginTask(JInMessages message) {
        super(message);
    }

    @Override
    public String[][] rules() {
        String[][] rules = {
                {"require", "email,password"}
        };
        return rules;
    }

    @Override
    public String rights() {
        String rigths = "guest";

        return rigths;
    }

    @Override
    public void action() {

        String email = this.message.json.get("email").toString();

        String password = this.message.json.get("password").toString();

        Users users = new Users();

        boolean isAuth = users.authorization(email, password);

        String outString;

        if (isAuth) {

            String token = users.getToken();
            int userId = users.id;

            String ip = this.message.ip;

            //System.out.println(insertQuery);

            Session session = new Session();

            session.setToken(userId, token, ip);


            outString = "{\"token\":\"" + token + "\"}";

        } else {
            outString = "{\"error\":\"Incorrect login or password \"}";
        }

        JOutMessages outMessage = new JOutMessages(this.message.ip, outString);
        outMessage.insert();

    }

}
