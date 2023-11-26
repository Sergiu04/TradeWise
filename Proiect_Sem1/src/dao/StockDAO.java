package dao;

import model.Stock;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    public Stock getStockById(int id) {
        String query = "SELECT * FROM stocks WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Stock(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("purchase_price"),
                            resultSet.getDouble("selling_price"),
                            resultSet.getDouble("automatic_sale_percentage"),
                            resultSet.getDouble("notification_decrease_percentage"),
                            resultSet.getDouble("prediction_gain_percentage")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stock", e);
        }
        return null;
    }

    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
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
                stocks.add(stock);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving stocks", e);
        }
        return stocks;
    }

    public void updateStock(Stock stock) {
        String query = "UPDATE stocks SET name = ?, purchase_price = ?, selling_price = ?, automatic_sale_percentage = ?, notification_decrease_percentage = ?, prediction_gain_percentage = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, stock.getName());
            statement.setDouble(2, stock.getPurchasePrice());
            statement.setDouble(3, stock.getSellingPrice());
            statement.setDouble(4, stock.getAutomaticSalePercentage());
            statement.setDouble(5, stock.getNotificationDecreasePercentage());
            statement.setDouble(6, stock.getPredictionGainPercentage());
            statement.setInt(7, stock.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating stock", e);
        }
    }
}
