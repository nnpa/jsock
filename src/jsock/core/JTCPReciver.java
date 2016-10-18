/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.core;

import jsock.message.JInMessages;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Recieve clien messages
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JTCPReciver extends Thread{

    /**
     * server port
     */
    private final int port;
    /**
     * pool size
     */
    private final int poolSize;
    /**
     * 
     */
    public static boolean isRunning = true;
    
    public JTCPReciver(int poolSize,int port){
        this.poolSize = poolSize;
        this.port     = port;
    }
    
    /**
     * Create thread poll 
     */
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ExecutorService executor   = Executors.newFixedThreadPool(poolSize);
            
            while(JTCPReciver.isRunning){
                Socket   socket  = serverSocket.accept();
                executor.execute(
                    new TCPReciverHandler(socket)
                );
            }
        } catch (IOException ex) {
            Logger.getLogger(JTCPReciver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Listen user sockets 
     * send messages to message list
     * send connection to connection list
     */
    class TCPReciverHandler implements Runnable{

        //socket
        Socket socket;
        
        private TCPReciverHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            String data = "";
            
            try {
                
                InputStream inStream = socket.getInputStream();
                
                Scanner scanner      = new Scanner(inStream);
                
                while(scanner.hasNextLine()){
                    data += scanner.nextLine();
                }
                
                InetAddress socketAdress = socket.getInetAddress(); 
                
                String ip = socketAdress.getHostAddress();
                
                //create connetion from socket and insert to table
                JConnections socketInfo    = new JConnections(ip);
                socketInfo.insert();
                
                //save input data to list
                //key is ip + : + update time
                //data is data id + : + data

                
                JInMessages inputSocketMessage = new JInMessages(ip,data);
                inputSocketMessage.insert();
                
            } catch (IOException ex) {
                Logger.getLogger(TCPReciverHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(JTCPReciver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}