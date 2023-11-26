package ui;

import dao.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField notificationThresholdField;

    public SettingsPage() {
        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridLayout(4, 2));

        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);

        emailField = new JTextField(20);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        JLabel notificationThresholdLabel = new JLabel("Notification Threshold:");
        panel.add(notificationThresholdLabel);

        notificationThresholdField = new JTextField(20);
        panel.add(notificationThresholdField);

        JButton saveButton = new JButton("Save");
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        panel.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Închide fereastra de setări
            }
        });
    }

    private void saveSettings() {
        // Validare
        String newEmail = emailField.getText();
        String newPassword = new String(passwordField.getPassword());
        String notificationThreshold = notificationThresholdField.getText();

        if (!isValidEmail(newEmail)) {
            showErrorMessage("Email is not valid.");
            return;
        }

        if (!isValidPassword(newPassword)) {
            showErrorMessage("Password must meet certain criteria.");
            return;
        }

        try {
            // Operații de salvare care pot dura o perioadă mai lungă se fac pe un alt fir de execuție
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Salvează setările (de exemplu, în baza de date)
                    UserDAO userDao = new UserDAO();
                    userDao.updateUserSettings(newEmail, newPassword, notificationThreshold);

                    // Aici poți implementa logica specifică aplicației
                    
                    System.out.println("New Email: " + newEmail);
                    System.out.println("New Password: " + newPassword);
                    System.out.println("Notification Threshold: " + notificationThreshold);

                    // Simulează o operație care durează
                    Thread.sleep(2000);

                    return null;
                }

                @Override
                protected void done() {
                    showSuccessMessage("Settings saved successfully.");
                    dispose(); // Închide fereastra de setări după salvare
                }
            };

            worker.execute();
        } catch (Exception ex) {
            showErrorMessage("An error occurred during the save operation.");
        }
    }

    private boolean isValidEmail(String email) {
        // Simplu: Verifică dacă conține un "@" și un "."
        return email != null && email.contains("@") && email.contains(".");
    }

    private boolean isValidPassword(String password) {
        // Simplu: Verifică dacă parola are cel puțin 8 caractere
        return password != null && password.length() >= 8;
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SettingsPage();
            }
        });
    }
}
