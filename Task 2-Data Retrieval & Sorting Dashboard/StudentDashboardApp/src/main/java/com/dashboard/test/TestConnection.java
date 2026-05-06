package com.dashboard.test;

import com.dashboard.dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("🔍 Testing Database Connection...");
        System.out.println("==================================");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ SUCCESS: Connected to database!");
                
                // Test query
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM students");
                
                if (rs.next()) {
                    System.out.println("📊 Total students in database: " + rs.getInt("count"));
                }
                
                // Show all tables
                rs = stmt.executeQuery("SHOW TABLES");
                System.out.println("\n📋 Tables in database:");
                while (rs.next()) {
                    System.out.println("   - " + rs.getString(1));
                }
                
                rs.close();
                stmt.close();
                System.out.println("==================================");
                System.out.println("✅ All tests passed!");
            } else {
                System.out.println("❌ FAILED: Could not connect to database!");
                System.out.println("\n🔧 Troubleshooting steps:");
                System.out.println("1. Is MySQL running?");
                System.out.println("2. Is username 'root' correct?");
                System.out.println("3. Did you set the correct password in DatabaseConnection.java?");
                System.out.println("4. Does database 'studentdb' exist?");
                System.out.println("5. Is MySQL port 3306 correct?");
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}