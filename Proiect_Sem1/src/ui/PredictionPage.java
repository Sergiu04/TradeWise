package ui;

import model.Stock;
import model.User;
import service.PredictionService;
import service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PredictionPage extends JFrame {
    private User user;
    private PredictionService predictionService;
    private AuthService authService;
    private JComboBox<String> stockSymbolComboBox;
    private JTextArea textArea;

    public PredictionPage(User user, PredictionService predictionService, AuthService AuthService) {
        this.user = user;
        this.predictionService = predictionService;
        this.authService = AuthService;

        // Set up the frame
        setTitle("Prediction Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel and add it to the frame
        JPanel panel = new JPanel();
        add(panel);

        // Create the components
        
        String[] availableSymbols = {"AAPL", "GOOGL", "MSFT", "AMZN"};
        stockSymbolComboBox = new JComboBox<>(availableSymbols);
        textArea = new JTextArea();
        JButton predictButton = new JButton("Predict");
        JButton viewHistoryButton = new JButton("View Prediction History");

        // Add the components to the panel
        panel.add(new JLabel("Select a stock symbol:"));
        panel.add(stockSymbolComboBox);
        panel.add(predictButton);
        panel.add(viewHistoryButton);
        panel.add(new JScrollPane(textArea));

        // Add action listeners to the buttons
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predictStockPrice();
            }
        });

        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPredictionHistory();
            }
        });

    }

    private void predictStockPrice() {
        // Get the selected stock symbol
        String selectedSymbol = (String) stockSymbolComboBox.getSelectedItem();

        // Check if a stock symbol is selected
        if (selectedSymbol == null || selectedSymbol.isEmpty()) {
            showErrorMessage("Please select a stock symbol.");
            return;
        }

        // Check if the prediction service is available
        /*if (predictionService == null) {
            showErrorMessage("Prediction service is not available.");
            return;
        }*/

        // Call the predictStockPrice method and set the prediction in the text area
        Stock stock = new Stock();
        stock.setName(selectedSymbol);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    // Simulate a long-running operation
                    Thread.sleep(2000);

                    // The prediction is done on a separate thread to keep the UI thread responsive
                    double prediction = predictionService.predictStockPrice(stock);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            textArea.setText("Predicted price for " + selectedSymbol + ": " + prediction);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    showErrorMessage("Error during prediction: " + e.getMessage());
                }
                return null;
            }
        };
        worker.execute();
    }

    private void viewPredictionHistory() {
        // Adaugă aici logica pentru a afișa istoricul predictiilor
        showInformationMessage("View Prediction History: Not implemented yet.");
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInformationMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        User user = new User();
        PredictionService predictionService = new  PredictionService();
        AuthService authService = new AuthService();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PredictionPage(user, predictionService, authService); // User și PredictionService vor fi furnizate în mod real
            }
        });
    }
}