/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import conf.JConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JClientTCPSession {
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
       JClientTCPSession tcpTest = new JClientTCPSession();
       tcpTest.test();
        
       
    }
    
    public void test(){
        Sender sender     = new Sender();
        sender.start();
        
        Receiver receiver = new Receiver();
        receiver.start();
    }
    
    class Sender extends Thread{
        @Override
        public void run(){
            try {
                InetAddress host = InetAddress.getLocalHost();
                Socket socket    = null;
                
                //String message = "{\"task\":\"auth.JLoginTask\",\"email\":\"jetananas@yandex.ru\",\"password\":\"test\"}";
                //8d68fcdbcfc38b7d78648ef8eb38b137

                    String message = "{\"task\":\"JLoginTask\",\"email\":\"jetananas@yandex.ru\",\"password\":\"P2lfNOeI\"}";
                    socket = new Socket(host.getHostName(), JConfig.server_port);
                    
                    //InputStream  inStream = socket.getInputStream();
                    PrintWriter socketOut;
                    
                    socketOut = new PrintWriter(socket.getOutputStream(), true);
                  
                    socketOut.println(message);
                    socket.close();
                    Thread.sleep(100);
                    
            } catch ( IOException | InterruptedException ex) {
                Logger.getLogger(JClientTCPSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class Receiver extends Thread{
        JSONObject jsonObj;
        JSONParser parser;
        
        @Override
        public void run(){
            try {

                ServerSocket serverSocket = new ServerSocket(JConfig.client_port);
                

                String data = "";
                
                while(true){
                    data = "";
                    Socket   socket      = serverSocket.accept();

                    InputStream inStream = socket.getInputStream();

                    Scanner scanner      = new Scanner(inStream);
                    
                    while(scanner.hasNextLine()){
                        data += scanner.nextLine();
                    }
                    System.out.println(data);

                }
                
            //} catch (IOException | ParseException ex) {
            } catch (IOException ex) {
                Logger.getLogger(JClientTCPSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
