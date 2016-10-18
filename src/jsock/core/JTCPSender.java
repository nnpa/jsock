/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsock.message.JOutMessages;

/**
 * Send messages to client
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JTCPSender extends Thread{
    /**
     * Run flag
     */
    public static boolean isRunning = true;
    /**
     * Size of pool
     */
    public int poolSize;
    /**
     * message
     */
    public JOutMessages message;
    /**
     * client port
     */
    public int clientPort;
    
    public JTCPSender(int poolSize,int clientPort){
        this.poolSize    = poolSize;
        this.clientPort  = clientPort;
    }
    
    @Override
    public void run() {
        //executors pool
        ExecutorService executor   = Executors.newFixedThreadPool(poolSize);
        
        while(JTCPSender.isRunning){
            
            for (String key : JOutMessages.getKeySet()) {
                try {
                    message     =  (JOutMessages) JOutMessages.get(key);
                    
                    //System.out.println(message.ip + "_" + message.json);
                    
                    Socket socket = new Socket(message.ip, clientPort);
                    
                    TCPSenderHandler senderHandler = new TCPSenderHandler(socket);
                    
                    executor.execute(senderHandler);
                    
                } catch (IOException ex) {
                    Logger.getLogger(JTCPSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    class TCPSenderHandler implements Runnable{

        //socket
        Socket socket;
        
        private TCPSenderHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try {
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                socketOut.println(message.json.toJSONString());
                
            } catch (IOException ex) {
                Logger.getLogger(JTCPSender.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
