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
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JTCPSender extends Thread{
    /**
     * Run stop flag
     */
    public static boolean isRunning = true;
    /**
     * Pool size
     */
    public int poolSize;
    /**
     * Out message
     */
    public JOutMessages message;
    /**
     * client port
     */
    public int clientPort;
    
    public Socket socket;
    
    public JTCPSender(int poolSize){
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
                    
                    
                    clientPort    = Integer.getInteger(message.port);

                    
                    socket = new Socket(message.ip, clientPort);
                    
                    TCPSenderHandler senderHandler = new TCPSenderHandler(socket);
                    
                    executor.execute(senderHandler);
                    
                } catch (IOException ex) {
                    Logger.getLogger(JTCPSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
         try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(JTCPSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class TCPSenderHandler implements Runnable{

        /**
         * socket
         */
        Socket socket;
        
        private TCPSenderHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try {
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                
               // System.out.println(message.json.toJSONString());
              //  socketOut.println(message.json.toJSONString());
                message = null;
                socket.close();
                
            } catch (IOException ex) {
                Logger.getLogger(JTCPSender.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
