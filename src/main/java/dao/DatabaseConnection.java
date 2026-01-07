package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:database/HEATDatabase.db";

    private DatabaseConnection() {
        try {
            File dbFolder = new File("database");
            if (!dbFolder.exists()) {
                dbFolder.mkdirs();
            }
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database successfully.");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}