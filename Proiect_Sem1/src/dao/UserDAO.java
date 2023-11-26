package dao;

import model.User;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public void updateUserSettings(String email, String password, String notificationThreshold) {
        String query = "UPDATE users SET email = ?, password = ?, notification_threshold = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, notificationThreshold);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user settings", e);
        }
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user", e);
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user", e);
        }
        return null;
    }
    
   
    public void insertUser(User user) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting user", e);
        }
    }

    public void updateUser(User user) {
        String query = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public void savePasswordResetToken(User user, String token) {
        String query = "UPDATE users SET password_reset_token = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving password reset token", e);
        }
    }
    
}


    
