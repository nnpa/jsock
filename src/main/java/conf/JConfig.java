/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package conf;


/**
 * JCongif stored framework settings
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JConfig {

    /**
     * module section
     * All modules must be created at modules directory and add
     * to modules array
     */
    public static String[] modules = {"TestModule"};

    /**
     * Net and socket section
     */

    /**
     * size of thread pool which send messages to clients
     */
    public static final int sender_pool = 300;
    /**
     * size of thread pool which receive messages from clients
     */
    public static final int receive_pool = 300;
    /**
     * Size of thread pool which execute tasks
     */
    public static final int task_pool = 300;

    //jnet_driver tcp udp

    /**
     * Switch socket type tcp or udp
     */
    public static final String protocol = "udp";


    /**
     * Connection life time use in  JConnections LIFE_TIME
     * Time after which the server close connection
     */
    public static final int connection_life_time = 60000;


    /**
     * Client port
     */
    public static final int client_port = 8092;
    /**
     * Server port
     */
    public static final int server_port = 8086;

    /**
     * Socket buffer size
     */
    public static final int socket_buffer_size = 1000;

    /**
     * Command executor section
     */

    /**
     * Timeout of command executor
     */
    public static final int executor_timeout = 19000;
    /**
     * Add command to command array
     */
    public static String[] executor_commands = {"JSystemGarbage"};

    /** Session.sessionLifeTime
     *  Session settings
     *  300 seconds for framework test
     */


    /**
     * Email section
     */
    //from message
    public static final String email_from = "ibaleksandrov@gmail.com";//change accordingly
    //email user
    public static final String email_user_name = "ibaleksandrov";//change accordingly
    //email password
    public static final String email_password = "xxxxx";//change accordingly
    //email auth
    public static final String email_auth = "true";
    //email ttls
    public static final String email_ttls = "true";
    //email host
    public static final String email_host = "smtp.gmail.com";
    //
    public static final String email_port = "587";

    /**
     * Database settings
     */

    /**
     * mysql user name
     */
    public static final String mysql_user = "root";
    /**
     * mysql user password
     */
    public static final String mysql_password = "";

    /**
     * jdbc mysql connection string
     */
    public static final String mysql_url = "jdbc:mysql://localhost:3306/jsock";
    /**
     * mysql db name
     */
    public static final String mysql_db_name = "jsock";


    public void main() {
    }
}
