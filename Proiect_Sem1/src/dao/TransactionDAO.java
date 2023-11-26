package dao;

import model.Transaction;
import model.User;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public Transaction getTransactionById(int id) {
        String query = "SELECT * FROM transactions WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Transaction(
                            resultSet.getInt("id"),
                            resultSet.getInt("stock_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("transaction_date"),
                            resultSet.getString("transaction_type")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transaction", e);
        }
        return null;
    }

    public List<Transaction> getAllTransactions(User user) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction(
                            resultSet.getInt("id"),
                            resultSet.getInt("stock_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("transaction_date"),
                            resultSet.getString("transaction_type")
                    );
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transactions", e);
        }
        return transactions;
    }

    public void addTransaction(User user, Transaction transaction) {
        String query = "INSERT INTO transactions (stock_id, user_id, transaction_date, transaction_type) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, transaction.getStockId());
            statement.setInt(2, user.getId());
            statement.setString(3, transaction.getTransactionDate());
            statement.setString(4, transaction.getTransactionType());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding transaction", e);
        }
    }
}
