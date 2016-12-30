/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package jsock.tests;

import conf.JConfig;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author nn
 */
public class JUDPServerRequest {
      
    public String token = "";
    
    public static void main(String[] args){
        JUDPServerRequest test = new JUDPServerRequest();
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

                byte[] buffer = "{\"task\":\"JLoginTask\",\"email\":\"jetananas@yandex.ru\",\"password\":\"BnKitMnX\"}".getBytes();
                
                //byte[] buffer = "{\"task\":\"JTestTask\",\"auth_token\":\"7ebc5d1781c51c50c864629299e6a5d91476670763\",\"message\":\"authorized\"}".getBytes();
                
                //byte[] buffer = "{\"task\":\"JTestTask\",\"message\":\"mymessage\",}".getBytes();

                //byte[] buffer = "{\"task\":\"JRegistrationTask\",\"email\":\"jetananas@yandex.ru\",}".getBytes();


                
                InetAddress receiverAddress = InetAddress.getLocalHost();
                
                DatagramPacket packet = new DatagramPacket(
                        buffer, buffer.length, receiverAddress, JConfig.server_port);
                
                
                datagramSocket.send(packet);
                
                
                try {
                    Thread.sleep(7000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(JUDPServerRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String queryString = "{\"task\":\"JRightsTask\",\"auth_token\":\""+token+"\",\"message\":\"example\"}";
                
                buffer = queryString.getBytes();

                packet = new DatagramPacket(
                         buffer, buffer.length, receiverAddress, JConfig.server_port);

                datagramSocket.send(packet);

                
                // datagramSocket.send(packet);
            
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
                    
                    
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(stringData);
                    
                    if(null != json.get("token")){
                        token = json.get("token").toString();
                    }
                    
                    if(null != json.get("ip_message")){
                        System.out.println(json.get("ip_message").toString());
                    }
                    
                    if(null != json.get("error")){
                        System.out.println(json.get("error").toString());
                    }
                    
                }
            } catch (SocketException ex) {
                Logger.getLogger(JClientUDPTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex ){
                Logger.getLogger(JClientUDPTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(JUDPServerRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
