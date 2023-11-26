package ui;

import model.*;
import model.Stock;
import service.StockService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StockSearchPage extends JFrame {
    private User user;
    private StockService stockService;

    public StockSearchPage(User user, StockService stockService) {
        this.user = user;
        this.stockService = stockService;

        setTitle("Stock Search");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add a search field
        JTextField searchField = new JTextField(20);
        panel.add(searchField);

        // Add a search button
        JButton searchButton = new JButton("Search");
        panel.add(searchButton);

        // Add a label to display the search results
        JLabel resultsLabel = new JLabel("");
        panel.add(resultsLabel);

        // Handle the search button click event
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificare pentru a evita căutarea simultană
                if (!searchButton.getText().equals("Search")) {
                    return;
                }
        
                // Change the button text to indicate that a search is in progress
                searchButton.setText("Căutare...");
        
                // Perform the search in a new thread to avoid blocking the UI
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String searchTerm = searchField.getText();
                            List<Stock> searchResults = stockService.searchStocks(searchTerm);
        
                            // Update the results label on the Event Dispatch Thread
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (searchResults.isEmpty()) {
                                        resultsLabel.setText("No stocks found.");
                                    } else {
                                        StringBuilder resultsText = new StringBuilder("<html>Found stocks:<br/>");
                                        for (Stock stock : searchResults) {
                                            resultsText.append(stock.getName()).append("<br/>");
                                        }
                                        resultsText.append("</html>");
                                        resultsLabel.setText(resultsText.toString());
                                    }
                                }
                            });
                        } catch (Exception ex) {
                            // Tratează excepțiile și oferă feedback utilizatorului
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    resultsLabel.setText("An error occurred during the search.");
                                }
                            });
                        } finally {
                            // Change the button text back to its original state
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    searchButton.setText("Search");
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        

    }
}
