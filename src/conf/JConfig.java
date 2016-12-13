/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package conf;


/**
 *  
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JConfig {
   
   /**
    * module section
    */
   public static String[] modules = { "TestModule" };
   
   /**
    * Net and socket section
    */
   //size of sender pool net module
   public static final int  sender_pool                  = 300;
   //size of resiver pool  net module
   public static final int  resiver_pool                 = 300;
   //size of resiver pool  net module  
   public static final int  task_pool                    = 300;
   
   //jnet_driver tcp udp    
   public  static final String protocol                  = "udp";


   //connection life time use in  JConnections LIFE_TIME
   public static final int  connection_life_time         = 500;

   //sender port
   public static final int  client_port                  = 8092;
   //resiver port
   public static final int  server_port                  = 8086;
  
   //socket buffer size
   public static final int  socket_buffer_size           = 1000;
   
   /**
    * Command executor section
    */
   //executor timeout 19 seconds by default
   public static final int  executor_timeout             = 19000;
   // tasks which must be executed, all tasks stored and
   // must be created  in commands folder 
   public static String[]   executor_tasks               = {"JSystemGarbage"};
   
   /** Session.sessionLifeTime
    *  Session settings 
    *  300 seconds for framework test
    */

   
   /**
    * Email section
    * 
    */
    //from message
    public static  final  String  email_from      = "ibaleksandrov@gmail.com";//change accordingly
    //email user
    public  static final  String  email_user_name = "ibaleksandrov";//change accordingly
    //email password
    public  static final  String  email_password  = "xxxxx";//change accordingly
    //email auth
    public  static final   String email_auth      = "true";
    //email ttls
    public  static final   String email_ttls      = "true";
    //email host
    public  static final   String email_host      = "smtp.gmail.com";
    //
    public  static final   String email_port      = "587";

   /**
    * Database settings
    */
   public static final String mysql_user          = "root";
   
   public static final String mysql_password      = "";
   
   public static final String mysql_url           = "jdbc:mysql://localhost:3306/jsock";

   public static final String mysql_db_name       = "jsock";

   
   public void main(){ }
}
