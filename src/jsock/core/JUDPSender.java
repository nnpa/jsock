package jsock.core;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsock.message.JOutMessages;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JUDPSender extends Thread{
    /**
     * run stop flag
     */
    public static boolean isRunning = true;
    /**
     * pool size
     */
    public int poolSize;
    /**
     * out passage
     */
    public JOutMessages message;
    /**
     * client port
     */
    public int clientPort;

    DatagramPacket packet;

    public JUDPSender(int poolSize){
        this.poolSize    = poolSize;
    }
    
    @Override
    public void run() {
        try{
            ExecutorService executor   = Executors.newFixedThreadPool(poolSize);
            DatagramSocket socket      =  new DatagramSocket();

            byte[] buffer;
            InetAddress inetAddress;
            JUDPSenderHandler handler;
            
            while(JUDPSender.isRunning){

                for (String key : JOutMessages.getKeySet()) {

                    message     =  (JOutMessages) JOutMessages.get(key);

                    buffer      = message.json.toJSONString().getBytes();
                    
                    inetAddress = InetAddress.getByName(message.ip);
                    
                    clientPort    = Integer.getInteger(message.port);

                    
                    packet = new DatagramPacket(buffer, buffer.length, inetAddress, clientPort);
                    
                    handler = new JUDPSenderHandler(socket,packet);
                    executor.execute(handler);

                } 
            }
            packet = null;
            socket.close();
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(JUDPSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class JUDPSenderHandler implements Runnable{

        /**
         * datagram socket
         */
        DatagramSocket socket;
        /**
         * datagram packet
         */
        DatagramPacket packet;
        
        public JUDPSenderHandler(DatagramSocket socket,DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
        }
        
        @Override
        public void run() {
            try {
                
                socket.send(packet);
                
                socket.close();
                packet = null;
            } catch (IOException ex) {
                Logger.getLogger(JUDPSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
