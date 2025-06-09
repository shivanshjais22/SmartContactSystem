package Email;

import org.springframework.stereotype.Service;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class Emailservices {

    public static boolean  sendemail(String message, String subject, String to) {
        String host = "smtp.gmail.com";

        
        boolean f=false;
        String from="shivanshnileshjais@gmail.com";
        
        Properties p = new Properties();
        
        p.put("mail.smtp.host", host);
        p.put("mail.smtp.port", "465");
        p.put("mail.smtp.ssl.enable", "true");
        p.put("mail.smtp.auth", "true");

        Session s = Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("shivanshnileshjais@gmail.com", "lcly vnwj xczl ctfe");
            }
        });

        s.setDebug(true);

        try {
            MimeMessage m = new MimeMessage(s);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            m.setContent(message,"text/html");

            Transport.send(m);
            System.out.println("Email sent successfully.");
            f=true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        
        
        return f;
    }
    
    
    
    
    
    }
    
  
