/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.core;

import conf.JConfig;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsock.message.JInMessages;

/**
 * 
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JUDPRecivier extends Thread{
    
    /**
     * server port
     */
    private final int port;
    /**
     * pool size
     */
    private final int poolSize;
    /**
     * run stop flag
     */
    public static boolean isRunning = true;
    
    byte[] receiveData;
    
    
    public JUDPRecivier(int poolSize,int port){
        this.port     = port;
        this.poolSize = poolSize;
    }
    
    /**
     * Create thread poll 
     */
    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            
            ExecutorService executor     = Executors.newFixedThreadPool(poolSize);
            
            
            while(JUDPRecivier.isRunning){
                
                receiveData           = new byte[JConfig.socket_buffer_size];

                DatagramPacket receivePacket = new DatagramPacket ( receiveData, receiveData.length );
                serverSocket.receive ( receivePacket );
                
                executor.execute(
                    new JUDPRecivierHandler(serverSocket,receivePacket)
                );
                
                receiveData = null;
            }
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(JTCPReciver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Listen user sockets 
     * send messages to message list
     * send connection to connection list
     */
    class JUDPRecivierHandler implements Runnable{
        //socket
        DatagramSocket socket;
        
        DatagramPacket receivePacket;
        
        public JUDPRecivierHandler(DatagramSocket serverSocket,DatagramPacket receivePacket) {
            this.socket        = serverSocket;
            this.receivePacket = receivePacket;
        }
        
        @Override
        public void run() {
            //String data = "";
            
            
            InetAddress inetAdress    = receivePacket.getAddress();
            String ip   = inetAdress.getHostAddress();
                        
            byte[] data = receivePacket.getData();
            
            String stringData = new String(data,0, receivePacket.getLength());
                        
            JInMessages inputSocketMessage = new JInMessages(ip,stringData);
            inputSocketMessage.insert();
        }
    }
}
