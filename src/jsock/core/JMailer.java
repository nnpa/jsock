/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package jsock.core;

import conf.JConfig;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author nn
 */
public class JMailer {
       /**
    * Email section
    * ibaleksandrov1988
    * 
    */
    //from message
    public   final  String  email_from      = "ibaleksandrov1988@gmail.com";
    //email user
    public   final  String  email_user_name = "ibaleksandrov1988";//change accordingly
    //email password
    public   final  String  email_password  = "g$xtkcb777G";//change accordingly
    //email auth
    public   final   String email_auth      = "true";
    //email ttls
    public   final   String email_ttls      = "true";
    //email host
    public   final   String email_host      = "smtp.gmail.com";
    //
    public   final   String email_port      = "587";
    
    /**
     * 
     * @param to
     * @param subject
     * @param text
     */
    public static void sendMail(String to,String subject,String text){
        

              // Sender's email ID needs to be mentioned
              String from           = JConfig.email_from;//change accordingly
              final String username = JConfig.email_user_name;//change accordingly
              final String password = JConfig.email_password;//change accordingly

              // Assuming you are sending email through relay.jangosmtp.net

              Properties props = new Properties();
              props.put("mail.smtp.auth", JConfig.email_auth);
              props.put("mail.smtp.starttls.enable", JConfig.email_ttls);
              props.put("mail.smtp.host", JConfig.email_host);
              props.put("mail.smtp.port", JConfig.email_port);

              // Get the Session object.
              javax.mail.Session session = javax.mail.Session.getInstance(props,
              new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                 }
              });

              try {
                 // Create a default MimeMessage object.
                 Message sdf = new MimeMessage(session);
                 
                 // Set From: header field of the header.
                 sdf.setFrom(new InternetAddress(from));

                 // Set To: header field of the header.
                 sdf.setRecipients(Message.RecipientType.TO,
                 InternetAddress.parse(to));

                 // Set Subject: header field
                 sdf.setSubject(subject);

                 // Now set the actual message
                 sdf.setText(text);

                 // Send message
                 Transport.send(sdf);


              } catch (MessagingException e) {
                    throw new RuntimeException(e);
              }
    }
}
