package com.dashboard.dao;

import com.dashboard.model.Student;
import java.sql.*;
import java.util.*;

public class StudentDAO {
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("❌ Connection is null!");
                return students;
            }
            
            System.out.println("✅ Connected to database, executing query...");
            
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                
                // Get metadata to see what columns exist
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                System.out.println("📊 Table columns:");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.println("   - " + metaData.getColumnName(i));
                }
                
                while (rs.next()) {
                    Student student = new Student();
                    
                    // Safely get each column with error handling
                    try {
                        student.setId(rs.getInt("id"));
                    } catch (SQLException e) {
                        System.err.println("⚠️ Column 'id' not found");
                    }
                    
                    try {
                        student.setName(rs.getString("name"));
                    } catch (SQLException e) {
                        System.err.println("⚠️ Column 'name' not found");
                    }
                    
                    try {
                        student.setDepartment(rs.getString("department"));
                    } catch (SQLException e) {
                        System.err.println("⚠️ Column 'department' not found");
                    }
                    
                    try {
                        student.setGrade(rs.getString("grade"));
                    } catch (SQLException e) {
                        System.err.println("⚠️ Column 'grade' not found");
                    }
                    
                    // FIXED: Use fully qualified java.sql.Date to avoid ambiguity
                    try {
                        java.sql.Date joinDate = rs.getDate("join_date");
                        if (joinDate != null) {
                            student.setJoinDate(joinDate.toString());
                        } else {
                            student.setJoinDate("N/A");
                        }
                    } catch (SQLException e) {
                        try {
                            // Try alternative column name
                            java.sql.Date joinDate = rs.getDate("joindate");
                            if (joinDate != null) {
                                student.setJoinDate(joinDate.toString());
                            } else {
                                student.setJoinDate("N/A");
                            }
                        } catch (SQLException e2) {
                            student.setJoinDate("N/A");
                        }
                    }
                    
                    try {
                        student.setStatus(rs.getString("status"));
                    } catch (SQLException e) {
                        student.setStatus("active");
                    }
                    
                    students.add(student);
                    System.out.println("✅ Added student: " + student.getName());
                }
                
                System.out.println("✅ Total students retrieved: " + students.size());
            }
            
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: " + e.getMessage());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        }
        return students;
    }
    
    public Map<String, Integer> getDepartmentCounts() {
        Map<String, Integer> deptCounts = new HashMap<>();
        String query = "SELECT department, COUNT(*) as count FROM students GROUP BY department";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("❌ Connection is null!");
                return deptCounts;
            }
            
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                
                while (rs.next()) {
                    String dept = rs.getString("department");
                    int count = rs.getInt("count");
                    deptCounts.put(dept, count);
                    System.out.println("📊 " + dept + ": " + count);
                }
                
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getting department counts: " + e.getMessage());
            e.printStackTrace();
        }
        return deptCounts;
    }
    
    public int getTotalStudents() {
        String query = "SELECT COUNT(*) as total FROM students";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("❌ Connection is null!");
                return 0;
            }
            
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                
                if (rs.next()) {
                    int total = rs.getInt("total");
                    System.out.println("📊 Total students: " + total);
                    return total;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error getting total students: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    // Alternative simpler method if you prefer
    public List<Student> getAllStudentsSimple() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                student.setGrade(rs.getString("grade"));
                
                // Simple date handling - using java.sql.Date
                java.sql.Date sqlDate = rs.getDate("join_date");
                student.setJoinDate(sqlDate != null ? sqlDate.toString() : "N/A");
                
                student.setStatus(rs.getString("status"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    
    // Test method
    public static void main(String[] args) {
        System.out.println("🔍 Testing StudentDAO...");
        System.out.println("==========================");
        
        StudentDAO dao = new StudentDAO();
        
        // Test 1: Check total students
        System.out.println("\n📊 Test 1: Getting total students");
        int total = dao.getTotalStudents();
        System.out.println("Result: " + total);
        
        // Test 2: Get all students
        System.out.println("\n📊 Test 2: Getting all students");
        List<Student> students = dao.getAllStudents();
        System.out.println("Students list size: " + students.size());
        
        if (!students.isEmpty()) {
            System.out.println("\n📝 First few students:");
            for (int i = 0; i < Math.min(3, students.size()); i++) {
                Student s = students.get(i);
                System.out.println("   " + (i+1) + ". " + s.getName() + 
                                 " - " + s.getDepartment() + 
                                 " - " + s.getJoinDate());
            }
        }
        
        // Test 3: Get department counts
        System.out.println("\n📊 Test 3: Getting department counts");
        Map<String, Integer> deptCounts = dao.getDepartmentCounts();
        System.out.println("Departments: " + deptCounts);
        
        System.out.println("==========================");
        System.out.println("✅ Test completed!");
    }
}