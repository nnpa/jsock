/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.tests;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import conf.JConfig;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JClientUDPTest {
    
    public String token = "7ebc5d1781c51c50c864629299e6a5d91467830206";
    
    public static void main(String[] args){
        JClientUDPTest test = new JClientUDPTest();
        test.init();
    }
    
    public void init(){
        UDPReciveThread  udpreciver = new UDPReciveThread();
        udpreciver.start();
        
        UDPSendThread udpsend = new UDPSendThread();
        udpsend.start();
    }
    
    class UDPSendThread extends Thread{
    
        @Override
        public void run() {
            try {
                DatagramSocket datagramSocket = new DatagramSocket();

                byte[] buffer = "{\"task\":\"LoginTask\",\"email\":\"jetananas@yandex.ru\",\"password\":\"test\"}".getBytes();
                
                //byte[] buffer = "{\"task\":\"JTestTask\",\"auth_token\":\"7ebc5d1781c51c50c864629299e6a5d91476670763\",\"message\":\"authorized\"}".getBytes();
                
                //byte[] buffer = "{\"task\":\"JTestTask\",\"message\":\"mymessage\",}".getBytes();

                
                InetAddress receiverAddress = InetAddress.getLocalHost();
                
                DatagramPacket packet = new DatagramPacket(
                        buffer, buffer.length, receiverAddress, JConfig.server_port);
                datagramSocket.send(packet);
            
            } catch (SocketException | UnknownHostException  ex) {
                Logger.getLogger(JClientUDPTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JClientUDPTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class  UDPReciveThread extends Thread{
    
        @Override
        public void run() {
            try {
                DatagramSocket serverSocket = new DatagramSocket(JConfig.client_port);
                DatagramPacket receivePacket;        
                byte[] receiveData          = new byte[512];
                
                while(true){
                    receivePacket = new DatagramPacket ( receiveData, receiveData.length );
                    serverSocket.receive ( receivePacket );
                    
                    byte[] data = new byte[receivePacket.getLength()];
                                   
                    data  = receivePacket.getData();

                    String stringData = new String(data,0, receivePacket.getLength());
                    
                    System.out.println(stringData);
                }
            } catch (SocketException ex) {
                Logger.getLogger(JClientUDPTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex ){
                Logger.getLogger(JClientUDPTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
