package ui;

import model.*;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPage extends JFrame {
    private User user;
    private DashboardService dashboardService;
    private JPanel panel;
    private DashboardModel dashboardModel;

    public DashboardPage(User user, DashboardService dashboardService) {
        this.user = user;
        this.dashboardService = dashboardService;

        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        add(panel);

        dashboardModel = new DashboardModel();
        initializeComponents(panel);

        pack(); // Ajustează dimensiunile ferestrei în funcție de conținut
        setLocationRelativeTo(null); // Centrează fereastra pe ecran
        setVisible(true);
    }

    private void initializeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Async 
                new DashboardUpdateWorker().execute();
            }
        });
        panel.add(refreshButton);

        displayPortfolio(panel);
        displayTransactionHistory(panel);
    }

    private void displayData(JPanel panel, String title, List<?> data) {
        panel.add(new JLabel(title));

        if (data.isEmpty()) {
            panel.add(new JLabel("No data available."));
        } else {
            for (Object item : data) {
                panel.add(new JLabel(item.toString()));
            }
        }
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private class DashboardUpdateWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            try {
                // Actualizează datele din serviciu
                List<Stock> updatedPortfolio = dashboardService.getPortofolio(user);
                List<Transaction> updatedTransactionHistory = dashboardService.getTransactionHistory(user);

                // Actualizează modelul de date
                dashboardModel.updateData(updatedPortfolio, updatedTransactionHistory);
            } catch (Exception e) {
                // Gestionează erorile și afișează un mesaj corespunzător
                e.printStackTrace();
            }
            return null;// Actualizează datele din serviciu
            
        }

        @Override
        protected void done() {
            // Actualizează interfața după ce operația este completă
            displayPortfolio(panel);
            displayTransactionHistory(panel);
            panel.revalidate();
            panel.repaint();
        }
    }

  


    private void displayPortfolio(JPanel panel) {
        List<Stock> portfolio = dashboardService.getPortofolio(user);

        panel.add(new JLabel("Portfolio:"));
        if (portfolio.isEmpty()) {
            panel.add(new JLabel("No stocks in the portfolio."));
        } else {
            for (Stock stock : portfolio) {
                panel.add(new JLabel(stock.toString()));
            }
        }
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spatiu intre componente
    }

    private void displayTransactionHistory(JPanel panel) {
        List<Transaction> transactionHistory = dashboardService.getTransactionHistory(user);

        panel.add(new JLabel("Transaction History:"));
        if (transactionHistory.isEmpty()) {
            panel.add(new JLabel("No transaction history."));
        } else {
            for (Transaction transaction : transactionHistory) {
                panel.add(new JLabel(transaction.toString()));
            }
        }
    }

    public static void main(String[] args) {
        // Exemplu de utilizare:
        User user = new User(1, "username", "email", "password", 1.0);
        DashboardService dashboardService = new DashboardService(); 
        new DashboardPage(user, dashboardService);
    }
}
