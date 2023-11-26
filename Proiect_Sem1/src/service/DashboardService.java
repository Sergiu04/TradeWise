package service;

import dao.PortofolioDAO;
import dao.TransactionDAO;
import model.Stock;
import model.Transaction;
import model.User;

import java.util.List;

public class DashboardService {
    private PortofolioDAO portofolioDAO;
    private TransactionDAO transactionDAO;

    public DashboardService() {
        this.portofolioDAO = new PortofolioDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public List<Stock> getPortofolio(User user) {
        return portofolioDAO.getPortofolio(user);
    }

    public List<Transaction> getTransactionHistory(User user) {
        return transactionDAO.getAllTransactions(user);
    }
}
