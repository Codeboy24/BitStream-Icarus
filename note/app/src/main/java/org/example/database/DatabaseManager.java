package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:notes.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        String createTableSQL = 
            "CREATE TABLE IF NOT EXISTS notes (" +
            "id TEXT PRIMARY KEY, " +
            "title TEXT NOT NULL, " +
            "content TEXT, " +
            "color TEXT, " +
            "created_at TEXT, " +
            "updated_at TEXT" +
            ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}