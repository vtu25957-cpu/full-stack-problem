package com.dashboard.dao;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "vtu24372_vick@6306";
    
    static {
        try {
            System.out.println("🔍 Attempting to load MySQL driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Failed to load MySQL driver!");
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() {
        System.out.println("🔌 Attempting database connection...");
        System.out.println("   URL: " + URL);
        System.out.println("   Username: " + USERNAME);
        
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Database connected successfully!");
            
            // Test if database exists
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getCatalogs();
            boolean dbFound = false;
            while (rs.next()) {
                String dbName = rs.getString(1);
                if (dbName.equalsIgnoreCase("studentdb")) {
                    dbFound = true;
                    System.out.println("✅ Database 'studentdb' found!");
                }
            }
            rs.close();
            
            if (!dbFound) {
                System.err.println("⚠️ Database 'studentdb' not found!");
            }
            
            // Test if table exists
            rs = metaData.getTables(null, null, "students", null);
            if (rs.next()) {
                System.out.println("✅ Table 'students' found!");
            } else {
                System.err.println("⚠️ Table 'students' not found!");
            }
            rs.close();
            
            return conn;
            
        } catch (SQLException e) {
            System.err.println("❌ Connection failed!");
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            
            if (e.getErrorCode() == 1045) {
                System.err.println("\n🔑 Access denied! Check your password:");
                System.err.println("   Password: " + PASSWORD);
            } else if (e.getErrorCode() == 1049) {
                System.err.println("\n🗄️ Database 'studentdb' doesn't exist!");
                System.err.println("   Create it with: CREATE DATABASE studentdb;");
            } else if (e.getErrorCode() == 0) {
                System.err.println("\n🌐 Cannot connect to MySQL server!");
                System.err.println("   Check if MySQL is running on localhost:3306");
            }
            
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("🔍 DATABASE CONNECTION TEST");
        System.out.println("==========================");
        Connection conn = getConnection();
        
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("==========================");
    }
}