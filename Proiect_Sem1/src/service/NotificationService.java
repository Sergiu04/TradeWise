package service;
import dao.UserDAO;
import model.User;
 
import javax.mail.*; 
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Properties;

public class NotificationService {

    public void sendNotification(User user, String message) {
        final String username = "your-email@gmail.com";
        final String password = "your-password";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); 

       Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("your-email@gmail.com"));
            msg.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail())
            );
            msg.setSubject("Stock Notification");
            msg.setText(message);

            Transport.send(msg);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
}

