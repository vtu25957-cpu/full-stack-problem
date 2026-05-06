package com.ordermanagement.servlet;

import com.ordermanagement.dao.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Order Management System - Test Page</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; background-color: #1a1e24; color: #e4e6eb; }");
        out.println(".container { max-width: 800px; margin: 0 auto; padding: 20px; background-color: #2d343f; border-radius: 12px; }");
        out.println("h2 { color: #a29bfe; }");
        out.println(".success { color: #00b894; }");
        out.println(".info { background-color: #252b34; padding: 10px; border-radius: 6px; margin: 10px 0; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>✅ Order Management System - Test Page</h2>");
        out.println("<div class='info'>");
        out.println("<p><strong>Server Status:</strong> <span class='success'>Running</span></p>");
        out.println("<p><strong>Context Path:</strong> " + request.getContextPath() + "</p>");
        out.println("<p><strong>Servlet Path:</strong> " + request.getServletPath() + "</p>");
        out.println("<p><strong>Server Info:</strong> " + getServletContext().getServerInfo() + "</p>");
        out.println("</div>");
        
        // Test database connection
        out.println("<h3>Database Connection Test:</h3>");
        out.println("<div class='info'>");
        try {
            DBConnection.testConnection();
            out.println("<p class='success'>✓ Database connection successful!</p>");
        } catch (Exception e) {
            out.println("<p style='color: #d63031;'>✗ Database connection failed: " + e.getMessage() + "</p>");
        }
        out.println("</div>");
        
        out.println("<p><a href='" + request.getContextPath() + "/orders' style='color: #6c5ce7;'>Go to Order Management Dashboard →</a></p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}