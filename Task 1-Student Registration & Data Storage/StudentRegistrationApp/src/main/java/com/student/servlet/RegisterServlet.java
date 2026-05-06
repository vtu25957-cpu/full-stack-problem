package com.student.servlet;

import com.student.model.Student;
import com.student.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            // Get parameters from form
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String dob = request.getParameter("dob");
            String department = request.getParameter("department");
            String phone = request.getParameter("phone");
            
            // Debug: Print received parameters
            System.out.println("=== Registration Attempt ===");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("DOB: " + dob);
            System.out.println("Department: " + department);
            System.out.println("Phone: " + phone);
            
            // Validate input
            if(name == null || email == null || dob == null || department == null || phone == null ||
               name.trim().isEmpty() || email.trim().isEmpty() || dob.trim().isEmpty() || 
               department.trim().isEmpty() || phone.trim().isEmpty()) {
                
                System.out.println("❌ Validation failed: Empty fields detected");
                request.setAttribute("message", "All fields are required!");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("registerResult.jsp").forward(request, response);
                return;
            }
            
            // Step 1: Get database connection
            System.out.println("1. Attempting to get database connection...");
            Connection conn = DBConnection.getConnection();
            
            // Step 2: Check if connection is null
            if (conn == null) {
                System.out.println("❌ FATAL: Database connection returned NULL!");
                request.setAttribute("message", "❌ Database connection failed! Please check:"
                        + "<br>1. MySQL is running"
                        + "<br>2. Database 'student_db' exists"
                        + "<br>3. Username/password is correct");
                request.setAttribute("messageType", "error");
                request.getRequestDispatcher("registerResult.jsp").forward(request, response);
                return;
            }
            
            System.out.println("✅ Database connection obtained successfully");
            
            // Step 3: Create student object
            Student student = new Student(name, email, dob, department, phone);
            
            // Step 4: Prepare SQL statement
            String sql = "INSERT INTO students (name, email, dob, department, phone) VALUES (?, ?, ?, ?, ?)";
            System.out.println("2. SQL Query: " + sql);
            
            // Step 5: Execute with try-with-resources
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                // Set parameters
                pstmt.setString(1, student.getName());
                pstmt.setString(2, student.getEmail());
                pstmt.setString(3, student.getDob());
                pstmt.setString(4, student.getDepartment());
                pstmt.setString(5, student.getPhone());
                
                System.out.println("3. Executing insert...");
                
                // Execute update
                int result = pstmt.executeUpdate();
                
                System.out.println("4. Insert result: " + result + " row(s) affected");
                
                if (result > 0) {
                    System.out.println("✅ Registration successful for: " + email);
                    request.setAttribute("message", "✅ Registration successful! Student has been added.");
                    request.setAttribute("messageType", "success");
                } else {
                    System.out.println("❌ Registration failed - no rows affected");
                    request.setAttribute("message", "❌ Registration failed. Please try again.");
                    request.setAttribute("messageType", "error");
                }
            }
            
            // Close connection
            DBConnection.closeConnection(conn);
            System.out.println("5. Connection closed");
            
        } catch (SQLException e) {
            System.out.println("❌ SQL Error occurred!");
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            
            // Check for specific errors
            String errorMessage;
            if (e.getMessage().contains("Duplicate entry")) {
                errorMessage = "❌ Email already exists! Please use a different email.";
            } else if (e.getMessage().contains("doesn't exist")) {
                errorMessage = "❌ Database 'student_db' doesn't exist! Please create it first.";
            } else if (e.getMessage().contains("Access denied")) {
                errorMessage = "❌ Database access denied! Check username/password in DBConnection.java";
            } else {
                errorMessage = "❌ Database error: " + e.getMessage();
            }
            
            request.setAttribute("message", errorMessage);
            request.setAttribute("messageType", "error");
            
        } catch (Exception e) {
            System.out.println("❌ Unexpected error occurred!");
            e.printStackTrace();
            request.setAttribute("message", "❌ Unexpected error: " + e.getMessage());
            request.setAttribute("messageType", "error");
        }
        
        // Forward to result page
        System.out.println("6. Forwarding to registerResult.jsp");
        request.getRequestDispatcher("registerResult.jsp").forward(request, response);
    }
}