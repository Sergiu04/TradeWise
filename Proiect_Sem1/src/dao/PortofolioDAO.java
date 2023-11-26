package dao;

import model.Stock;
import model.User;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PortofolioDAO {
    public List<Stock> getAllStocks() {
        List<Stock> allStocks = new ArrayList<>();
        String query = "SELECT * FROM stocks";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Stock stock = new Stock(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("purchase_price"),
                        resultSet.getDouble("selling_price"),
                        resultSet.getDouble("automatic_sale_percentage"),
                        resultSet.getDouble("notification_decrease_percentage"),
                        resultSet.getDouble("prediction_gain_percentage")
                );
                allStocks.add(stock);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all stocks", e);
        }
        return allStocks;
    }

    
    public List<Stock> getPortofolio(User user) {
        List<Stock> portofolio = new ArrayList<>();
        String query = "SELECT * FROM portofolio WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Stock stock = new Stock(
                            resultSet.getInt("stock_id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("purchase_price"),
                            resultSet.getDouble("selling_price"),
                            resultSet.getDouble("automatic_sale_percentage"),
                            resultSet.getDouble("notification_decrease_percentage"),
                            resultSet.getDouble("prediction_gain_percentage")
                    );
                    portofolio.add(stock);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving portofolio", e);
        }
        return portofolio;
    }

    public void addStockToPortofolio(User user, Stock stock) {
        String query = "INSERT INTO portofolio (user_id, stock_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, stock.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding stock to portofolio", e);
        }
    }
    public boolean userOwnsStock(User user, Stock stock) {
        String query = "SELECT * FROM portofolio WHERE user_id = ? AND stock_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, stock.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if user owns stock", e);
        }
    }

    public void updateStock(Stock stock) {
        String query = "UPDATE portofolio SET automatic_sale_percentage = ? WHERE stock_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, stock.getAutomaticSalePercentage());
            statement.setInt(2, stock.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating stock in portofolio", e);
        }
    }
    
    
    public void removeStockFromPortofolio(User user, Stock stock) {
        String query = "DELETE FROM portofolio WHERE user_id = ? AND stock_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, stock.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error removing stock from portofolio", e);
        }
    }

    public void updatePortofolio(User user, Stock stock) {
        String query = "UPDATE portofolio SET stock_id = ? WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stock.getId());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating portofolio", e);
        }
    }

    public int getQuantityOfStock(User user, Stock stock) {
        String query = "SELECT quantity FROM portofolio WHERE user_id = ? AND stock_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, stock.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("quantity");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting quantity of stock", e);
        }
    }
    
}
