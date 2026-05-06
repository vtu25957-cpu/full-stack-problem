package com.logging.controller;

import com.logging.dao.LoggingDAO;
import com.logging.model.AuditLog;
import com.logging.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class LoggingController extends HttpServlet {
    private LoggingDAO loggingDAO;
    
    @Override
    public void init() {
        loggingDAO = new LoggingDAO();
        System.out.println("✅ LoggingController initialized!");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        System.out.println("📝 Request path: " + path);
        
        if (path.equals("/") || path.isEmpty()) {
            showDashboard(request, response);
        } else if (path.equals("/users")) {
            showUsers(request, response);
        } else if (path.equals("/logs")) {
            showLogs(request, response);
        } else if (path.equals("/reports")) {
            showReports(request, response);
        } else if (path.equals("/analytics")) {  // MAKE SURE THIS LINE EXISTS
            showAnalytics(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            addUser(request, response);
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            request.setAttribute("totalUsers", loggingDAO.getTotalUsers());
            request.setAttribute("todayActions", loggingDAO.getTodayActions());
            request.setAttribute("totalLogs", loggingDAO.getTotalLogs());
            request.setAttribute("activeUsers", loggingDAO.getActiveUsers());
            request.setAttribute("recentLogs", loggingDAO.getRecentLogs(10));
            request.setAttribute("dailyActivity", loggingDAO.getDailyActivity());
            
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void showUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<User> users = loggingDAO.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void showLogs(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<AuditLog> logs = loggingDAO.getAllLogs();
            request.setAttribute("logs", logs);
            request.getRequestDispatcher("/WEB-INF/views/logs.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void showReports(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            request.setAttribute("dailyActivity", loggingDAO.getDailyActivity());
            request.getRequestDispatcher("/WEB-INF/views/reports.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void showAnalytics(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Map<String, Object> analytics = loggingDAO.getAnalytics();
            request.setAttribute("analytics", analytics);
            
            // Also pass total counts for the health section
            request.setAttribute("totalUsers", loggingDAO.getTotalUsers());
            request.setAttribute("totalLogs", loggingDAO.getTotalLogs());
            
            request.getRequestDispatcher("/WEB-INF/views/analytics.jsp").forward(request, response);
            System.out.println("✅ Analytics page loaded");
        } catch (Exception e) {
            System.err.println("❌ Error loading analytics: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    private void addUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            User user = new User();
            user.setUsername(request.getParameter("username"));
            user.setEmail(request.getParameter("email"));
            user.setFullName(request.getParameter("fullName"));
            user.setRole(request.getParameter("role"));
            
            String ipAddress = request.getRemoteAddr();
            boolean success = loggingDAO.addUser(user, ipAddress);
            
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}