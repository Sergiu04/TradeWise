package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "sergiu01";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
