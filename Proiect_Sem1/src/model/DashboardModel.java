package model;
import java.util.List;
  // Modelul de date pentru a gestiona datele într-un mod centralizat
  public class DashboardModel {
    private List<Stock> portfolio;
    private List<Transaction> transactionHistory;

    public List<Stock> getPortfolio() {
        return portfolio;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void updateData(List<Stock> updatedPortfolio, List<Transaction> updatedTransactionHistory) {
        this.portfolio = updatedPortfolio;
        this.transactionHistory = updatedTransactionHistory;
    }
}