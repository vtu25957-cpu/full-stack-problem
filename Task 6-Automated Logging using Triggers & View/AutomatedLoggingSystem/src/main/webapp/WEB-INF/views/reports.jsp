<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daily Reports - Automated Logging System</title>
    <style>
        /* Copy base styles from users.jsp */
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', sans-serif; background-color: #1a1e24; color: #e0e0e0; }
        .container { max-width: 1400px; margin: 0 auto; padding: 20px; }
        .header { background: linear-gradient(135deg, #252b33, #2d343d); padding: 1.5rem; border-radius: 12px; margin-bottom: 2rem; border: 1px solid #404854; }
        .header h1 { color: #4a9eff; font-size: 2.5rem; }
        .header p { color: #b0b0b0; }
        .nav-bar { background-color: #252b33; padding: 1rem; border-radius: 8px; margin-bottom: 2rem; border: 1px solid #404854; }
        .nav-bar ul { list-style: none; display: flex; gap: 1.5rem; flex-wrap: wrap; }
        .nav-bar a { color: #e0e0e0; text-decoration: none; padding: 0.5rem 1rem; border-radius: 6px; }
        .nav-bar a:hover { background-color: #3a424d; color: #4a9eff; }
        .nav-bar a.active { background-color: #4a9eff; color: white; }
        .data-table { width: 100%; background-color: #252b33; border-radius: 8px; border: 1px solid #404854; border-collapse: collapse; margin-top: 1rem; }
        .data-table thead { background-color: #2d343d; }
        .data-table th { padding: 1rem; text-align: left; color: #4a9eff; border-bottom: 2px solid #404854; }
        .data-table td { padding: 0.75rem 1rem; border-top: 1px solid #404854; color: #b0b0b0; }
        .data-table tbody tr:hover { background-color: #3a424d; }
        .footer { background-color: #252b33; padding: 1.5rem; border-radius: 8px; margin-top: 2rem; text-align: center; border: 1px solid #404854; color: #b0b0b0; }
        
        /* Report-specific styles */
        .summary-cards {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 1rem;
            margin-bottom: 2rem;
        }
        
        .summary-card {
            background-color: #252b33;
            padding: 1.5rem;
            border-radius: 8px;
            text-align: center;
            border: 1px solid #404854;
        }
        
        .summary-value {
            font-size: 2rem;
            font-weight: bold;
            color: #4a9eff;
        }
        
        .summary-label {
            color: #b0b0b0;
            margin-top: 0.5rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>📊 Daily Activity Reports</h1>
            <p>View database activity aggregated by day</p>
        </header>
        
        <nav class="nav-bar">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/users">User Management</a></li>
                <li><a href="${pageContext.request.contextPath}/logs">Audit Logs</a></li>
                <li><a href="${pageContext.request.contextPath}/reports" class="active">Daily Reports</a></li>
                <li><a href="${pageContext.request.contextPath}/analytics">Analytics</a></li>
            </ul>
        </nav>
        
        <!-- Summary Cards -->
        <c:if test="${not empty dailyActivity}">
            <div class="summary-cards">
                <div class="summary-card">
                    <div class="summary-value">${dailyActivity[0].totalActions}</div>
                    <div class="summary-label">Today's Total</div>
                </div>
                <div class="summary-card">
                    <div class="summary-value" style="color: #51cf66;">${dailyActivity[0].inserts}</div>
                    <div class="summary-label">Inserts</div>
                </div>
                <div class="summary-card">
                    <div class="summary-value" style="color: #ffd43b;">${dailyActivity[0].updates}</div>
                    <div class="summary-label">Updates</div>
                </div>
                <div class="summary-card">
                    <div class="summary-value" style="color: #ff6b6b;">${dailyActivity[0].deletes}</div>
                    <div class="summary-label">Deletes</div>
                </div>
            </div>
        </c:if>
        
        <!-- Reports Table -->
        <h2 style="color: #4a9eff; margin: 2rem 0 1rem;">📅 Activity History</h2>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Total Actions</th>
                    <th>Inserts</th>
                    <th>Updates</th>
                    <th>Deletes</th>
                    <th>Unique Users</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${dailyActivity}" var="activity">
                    <tr>
                        <td><strong>${activity.activityDate}</strong></td>
                        <td style="font-weight: bold;">${activity.totalActions}</td>
                        <td style="color: #51cf66;">${activity.inserts}</td>
                        <td style="color: #ffd43b;">${activity.updates}</td>
                        <td style="color: #ff6b6b;">${activity.deletes}</td>
                        <td>${activity.uniqueUsers}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dailyActivity}">
                    <tr>
                        <td colspan="6" style="text-align: center; padding: 2rem; color: #b0b0b0;">
                            No report data available
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <footer class="footer">
            <p>© 2026 Automated Logging System - Daily Activity Reports</p>
        </footer>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>