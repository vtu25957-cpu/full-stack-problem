<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.dashboard.dao.DatabaseConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Database Test</title>
</head>
<body>
    <h2>Simple Database Test</h2>
    
    <%
        try {
            // Test 1: Check if driver loads
            Class.forName("com.mysql.cj.jdbc.Driver");
            out.println("<p style='color:green'>✅ Driver loaded</p>");
            
            // Test 2: Try to connect
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                "root",
                "vtu24372_vick@6306"
            );
            out.println("<p style='color:green'>✅ Connected to database</p>");
            
            // Test 3: Query the table
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            
            out.println("<h3>Students:</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Department</th></tr>");
            
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            out.println("<p style='color:red'>❌ Error: " + e.getMessage() + "</p>");
            e.printStackTrace(response.getWriter());
        }
    %>
    
    <p><a href="studentDashboard.jsp">Back to Dashboard</a></p>
</body>
</html>