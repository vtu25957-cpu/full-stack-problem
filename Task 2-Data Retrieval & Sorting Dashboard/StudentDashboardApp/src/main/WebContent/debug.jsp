<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.dashboard.dao.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Debug Page</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        .success { color: green; }
        .error { color: red; }
        pre { background: #f4f4f4; padding: 10px; }
    </style>
</head>
<body>
    <h2>Database Connection Debug</h2>
    
    <h3>System Info:</h3>
    <pre>
Java Version: <%= System.getProperty("java.version") %>
Working Dir: <%= application.getRealPath("/") %>
Classpath Check: 
    </pre>
    
    <h3>Driver Check:</h3>
    <%
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            out.println("<p class='success'>✅ Driver found in classpath!</p>");
        } catch (ClassNotFoundException e) {
            out.println("<p class='error'>❌ Driver NOT found!</p>");
            out.println("<pre>" + e.toString() + "</pre>");
        }
    %>
    
    <h3>Connection Test:</h3>
    <%
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                out.println("<p class='success'>✅ Connection successful!</p>");
                
                // Test query
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM students");
                if (rs.next()) {
                    out.println("<p>Total students: " + rs.getInt("count") + "</p>");
                }
                rs.close();
                stmt.close();
                conn.close();
            } else {
                out.println("<p class='error'>❌ Connection returned null!</p>");
            }
        } catch (Exception e) {
            out.println("<p class='error'>❌ Connection failed!</p>");
            out.println("<pre>" + e.toString() + "</pre>");
        }
    %>
    
    <h3>WEB-INF/lib Contents:</h3>
    <%
        String libPath = application.getRealPath("/WEB-INF/lib");
        java.io.File libDir = new java.io.File(libPath);
        if (libDir.exists()) {
            String[] files = libDir.list();
            out.println("<ul>");
            for (String file : files) {
                out.println("<li>" + file + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>lib directory not found: " + libPath + "</p>");
        }
    %>
    
    <p><a href="studentDashboard.jsp">Back to Dashboard</a></p>
</body>
</html>