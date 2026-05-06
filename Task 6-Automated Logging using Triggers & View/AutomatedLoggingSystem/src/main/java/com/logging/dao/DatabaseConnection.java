package com.logging.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // UPDATE THESE WITH YOUR ACTUAL CREDENTIALS
    private static final String URL = "jdbc:mysql://localhost:3306/logging_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";  // Your MySQL username
    private static final String PASSWORD = "vtu24372_vick@6306";      // Your MySQL password (empty if none)
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver loaded");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Connection failed! URL: " + URL);
            System.err.println("❌ Username: " + USERNAME);
            System.err.println("❌ Error: " + e.getMessage());
            throw e;
        }
    }
    
    // Test method - run this to check connection
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("✅ Connection test successful!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Connection test failed!");
            e.printStackTrace();
        }
    }
}