package com.ordermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Update these credentials according to your MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/order_management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root"; // Change this to your MySQL username
    private static final String PASSWORD = "vtu24372_vick@6306"; // Change this to your MySQL password
    
    static {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC Driver!");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connected successfully!");
                System.out.println("Connected to: " + conn.getMetaData().getURL());
                System.out.println("Database: " + conn.getMetaData().getDatabaseProductName());
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to database!");
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        testConnection();
    }
}