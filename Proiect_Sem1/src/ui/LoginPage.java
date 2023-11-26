package ui;

import service.*;
import model.*;
import dao.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
   // private AuthService authService;


    public LoginPage(){
        setTitle("Login Page");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new FlowLayout());

        panel.add(new JLabel("User"));
        usernameField = new JTextField(20);
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        registerButton = new JButton("Register");
        panel.add(registerButton);

        JButton recoverPasswordButton = new JButton("Recover Password");
        panel.add(recoverPasswordButton);


        
        recoverPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the password recovery page
                String email = JOptionPane.showInputDialog("Enter your email address:");
                if (email != null) {
                    // Use the AuthService to send a password recovery email
                    AuthService authService = new AuthService();
                     boolean success = authService.sendPasswordRecoveryEmail(email);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Password recovery email sent.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error sending password recovery email.");
                    }
                }
            }
        }); 

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username and password entered by the user
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
        
                // Use the AuthService to authenticate the user
                AuthService authService = new AuthService();
                User user = authService.login(username, password);
        
                if (user != null) {
                        // If the user is authenticated, navigate to the dashboard 
                   DashboardService dashboardService = new DashboardService();
                   DashboardPage dashboardPage = new DashboardPage(user, dashboardService);
                   dashboardPage.setVisible(true);
                    dispose();  // Close the login page
                   // goToDashboardPage(user);
                } else {
                    
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // goToRegistrationPage();
            }
        });
        
        

        
    }

    public static void main(String[] args) {
        //AuthService authService = new AuthService();
        new LoginPage();
    }
}
