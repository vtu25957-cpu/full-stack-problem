package com.student.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "vtu24372_vick@6306";
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Step 1: Load driver
            System.out.println("📌 Loading MySQL driver...");
            Class.forName(DRIVER);
            System.out.println("✅ Driver loaded");
            
            // Step 2: Try to connect
            System.out.println("📌 Connecting to database...");
            System.out.println("   URL: " + URL);
            System.out.println("   Username: " + USERNAME);
            
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            if (conn != null) {
                System.out.println("✅ Database connected successfully!");
            } else {
                System.out.println("❌ Connection returned null!");
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found!");
            System.out.println("   → Make sure mysql-connector.jar is in WEB-INF/lib");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Cannot connect to database!");
            System.out.println("   Error Code: " + e.getErrorCode());
            System.out.println("   SQL State: " + e.getSQLState());
            System.out.println("   Message: " + e.getMessage());
            
            // Helpful messages
            if (e.getMessage().contains("Access denied")) {
                System.out.println("🔴 FIX: Check username/password in DBConnection.java");
                System.out.println("   Current: username='" + USERNAME + "', password='" + PASSWORD + "'");
            }
            if (e.getMessage().contains("Unknown database")) {
                System.out.println("🔴 FIX: Database 'student_db' doesn't exist");
                System.out.println("   Run: CREATE DATABASE student_db;");
            }
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("🔴 FIX: MySQL is not running!");
                System.out.println("   Start MySQL service first");
            }
            e.printStackTrace();
        }
        
        return conn;
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Database connection closed");
            } catch (SQLException e) {
                System.out.println("❌ Error closing connection");
                e.printStackTrace();
            }
        }
    }
}