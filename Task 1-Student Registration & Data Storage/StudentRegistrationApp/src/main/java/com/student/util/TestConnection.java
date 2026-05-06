package com.student.util;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("🔍 TESTING DATABASE CONNECTION");
        System.out.println("================================");
        
        java.sql.Connection conn = DBConnection.getConnection();
        
        if (conn != null) {
            System.out.println("\n✅ SUCCESS! Database connection working!");
            DBConnection.closeConnection(conn);
        } else {
            System.out.println("\n❌ FAILED! Database connection is NULL!");
            System.out.println("\n🔧 TROUBLESHOOTING STEPS:");
            System.out.println("1. Is MySQL running?");
            System.out.println("2. Is database 'student_db' created?");
            System.out.println("3. Is password correct in DBConnection.java?");
        }
    }
}