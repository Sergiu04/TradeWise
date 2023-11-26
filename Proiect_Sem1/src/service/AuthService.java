package service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import dao.UserDAO;
import model.User;

public class AuthService{
    private UserDAO UserDAO;

    public AuthService() {
        this.UserDAO = new UserDAO();
    }

    public User signup(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashPassword(password)); 
        UserDAO.insertUser(user);
        return user;
    }

    public User login(String email, String password) {
        User user = UserDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return user;
        }
        return null;
    }

    public void signout(User user) {
        user = null;
    }

    public void resetPassword(String email, String newPassword) {
        User user = UserDAO.getUserByEmail(email);
        if (user != null) {
            user.setPassword(hashPassword(newPassword));
            UserDAO.updateUser(user);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

   /* 
    public boolean sendPasswordRecoveryEmail(String email) throws MessagingException{
        // Find the user with the given email
        User user = UserDAO.getUserByEmail(email);
        if (user == null) {
            // If no such user exists, return false
            return false;
        }
    
        // Generate a password reset token
        String token = UUID.randomUUID().toString();
    
        // Save the token in the database associated with the user
        UserDAO.savePasswordResetToken(user, token);
    
        // Create a password reset link
        String link = "https://www.yourwebsite.com/reset_password?token=" + token;
       
        // Send the link to the user's email
        try {
            sendEmail(email, "Password Reset", "To reset your password, click the following link: " + link);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    private void sendEmail(String to, String subject, String content) throws MessagingException {
        // Set up the email server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); //TLS
    
        // Create the email
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("your-email@gmail.com", "your-password");
                    }
                });
    
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("your-email@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
        );
        message.setSubject(subject);
        message.setText(content);
    
        // Send the email
        Transport.send(message);
    }
    
    
    
    */
    
}
