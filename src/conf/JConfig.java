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
   //loadable modules 
   public static String[] modules = { "TestModule" };
   
   /**
    * Core and net section
    * jsok.net section
    * 
    */
   //size of sender pool net module
   public static final int  sender_pool                  = 300;
   //size of resiver pool  net module
   public static final int  resiver_pool                 = 300;
   //size of resiver pool  net module  
   public static final int  task_pool                    = 300;
   
   //jnet_driver tcp udp    
   public static final String protocol                   = "udp";


   //connection life time
   public static final int  connection_life_time         = 500;

   //sender port
   public static final int  client_port                  = 8088;
   //resiver port
   public static final int  server_port                  = 8084;
  
   //socket buffer size
   public static final int  socket_buffer_size           = 1000;
   //garbage timeout
   public static final int  garbage_timeout              = 10000;

   /**
    * Database settings
    */
   public static final String mysql_user          = "root";
   
   public static final String mysql_password      = "";
   
   public static final String mysql_url           = "jdbc:mysql://localhost:3306/jsock";

   public static final String mysql_db_name       = "jsock";


   public void main(){ }
}
