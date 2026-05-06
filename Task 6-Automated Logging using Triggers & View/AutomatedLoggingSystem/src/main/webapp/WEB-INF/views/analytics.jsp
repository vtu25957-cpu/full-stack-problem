<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Analytics - Automated Logging System</title>
    <style>
        /* Dark Theme Styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #1a1e24;
            color: #e0e0e0;
            line-height: 1.6;
        }
        
        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .header {
            background: linear-gradient(135deg, #252b33, #2d343d);
            padding: 1.5rem;
            border-radius: 12px;
            margin-bottom: 2rem;
            border: 1px solid #404854;
        }
        
        .header h1 {
            color: #4a9eff;
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
        }
        
        .header p {
            color: #b0b0b0;
        }
        
        .nav-bar {
            background-color: #252b33;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 2rem;
            border: 1px solid #404854;
        }
        
        .nav-bar ul {
            list-style: none;
            display: flex;
            gap: 1.5rem;
            flex-wrap: wrap;
        }
        
        .nav-bar a {
            color: #e0e0e0;
            text-decoration: none;
            padding: 0.5rem 1rem;
            border-radius: 6px;
        }
        
        .nav-bar a:hover {
            background-color: #3a424d;
            color: #4a9eff;
        }
        
        .nav-bar a.active {
            background-color: #4a9eff;
            color: white;
        }
        
        .analytics-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1.5rem;
        }
        
        .analytics-card {
            background-color: #252b33;
            border-radius: 10px;
            padding: 1.5rem;
            border: 1px solid #404854;
        }
        
        .analytics-card h3 {
            color: #4a9eff;
            margin-bottom: 1.5rem;
            border-bottom: 2px solid #404854;
            padding-bottom: 0.5rem;
        }
        
        .stat-item {
            display: flex;
            justify-content: space-between;
            padding: 0.75rem 0;
            border-bottom: 1px solid #404854;
        }
        
        .stat-item:last-child {
            border-bottom: none;
        }
        
        .stat-label {
            color: #b0b0b0;
        }
        
        .stat-value {
            color: #4a9eff;
            font-weight: bold;
        }
        
        .progress-bar {
            background-color: #2d343d;
            height: 8px;
            border-radius: 4px;
            margin: 0.5rem 0;
            overflow: hidden;
        }
        
        .progress-fill {
            height: 100%;
            background-color: #4a9eff;
            border-radius: 4px;
        }
        
        .distribution-container {
            display: flex;
            align-items: flex-end;
            gap: 1.5rem;
            padding: 1rem 0;
            height: 200px;
        }
        
        .distribution-bar {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 0.5rem;
        }
        
        .bar {
            width: 100%;
            height: 150px;
            background-color: #2d343d;
            border-radius: 4px 4px 0 0;
            position: relative;
        }
        
        .bar-fill {
            position: absolute;
            bottom: 0;
            width: 100%;
            background-color: #4a9eff;
            border-radius: 4px 4px 0 0;
        }
        
        .bar-insert .bar-fill { background-color: #51cf66; }
        .bar-update .bar-fill { background-color: #ffd43b; }
        .bar-delete .bar-fill { background-color: #ff6b6b; }
        
        .footer {
            background-color: #252b33;
            padding: 1.5rem;
            border-radius: 8px;
            margin-top: 2rem;
            text-align: center;
            border: 1px solid #404854;
            color: #b0b0b0;
        }
        
        @media (max-width: 768px) {
            .analytics-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>📈 Analytics Dashboard</h1>
            <p>Advanced analytics and insights from your audit logs</p>
        </header>
        
        <nav class="nav-bar">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/users">User Management</a></li>
                <li><a href="${pageContext.request.contextPath}/logs">Audit Logs</a></li>
                <li><a href="${pageContext.request.contextPath}/reports">Daily Reports</a></li>
                <li><a href="${pageContext.request.contextPath}/analytics" class="active">Analytics</a></li>
            </ul>
        </nav>
        
        <div class="analytics-grid">
            <!-- Most Active Users -->
            <div class="analytics-card">
                <h3>👤 Most Active Users</h3>
                <c:choose>
                    <c:when test="${not empty analytics.activeUsers}">
                        <c:forEach items="${analytics.activeUsers}" var="user">
                            <div class="stat-item">
                                <span class="stat-label">${user.username}</span>
                                <span class="stat-value">${user.actionCount} actions</span>
                            </div>
                            <div class="progress-bar">
                                <div class="progress-fill" style="width: ${(user.actionCount / analytics.maxActions) * 100}%;"></div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; color: #b0b0b0; padding: 2rem;">
                            No user activity data available
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <!-- Table Activity -->
            <div class="analytics-card">
                <h3>📊 Table Activity</h3>
                <c:choose>
                    <c:when test="${not empty analytics.tableActivity}">
                        <c:forEach items="${analytics.tableActivity}" var="table">
                            <div class="stat-item">
                                <span class="stat-label">${table.tableName}</span>
                                <span class="stat-value">${table.actionCount} actions</span>
                            </div>
                            <div class="progress-bar">
                                <div class="progress-fill" style="width: ${(table.actionCount / analytics.maxTableActions) * 100}%;"></div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; color: #b0b0b0; padding: 2rem;">
                            No table activity data available
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <!-- Action Distribution -->
            <div class="analytics-card">
                <h3>🔄 Action Distribution</h3>
                <div class="distribution-container">
                    <div class="distribution-bar bar-insert">
                        <div class="bar">
                            <div class="bar-fill" style="height: ${analytics.insertPercent}%;"></div>
                        </div>
                        <div class="stat-value">${analytics.insertPercent}%</div>
                        <div class="stat-label">INSERT</div>
                    </div>
                    <div class="distribution-bar bar-update">
                        <div class="bar">
                            <div class="bar-fill" style="height: ${analytics.updatePercent}%;"></div>
                        </div>
                        <div class="stat-value">${analytics.updatePercent}%</div>
                        <div class="stat-label">UPDATE</div>
                    </div>
                    <div class="distribution-bar bar-delete">
                        <div class="bar">
                            <div class="bar-fill" style="height: ${analytics.deletePercent}%;"></div>
                        </div>
                        <div class="stat-value">${analytics.deletePercent}%</div>
                        <div class="stat-label">DELETE</div>
                    </div>
                </div>
            </div>
            
            <!-- System Health -->
            <div class="analytics-card">
                <h3>⚡ System Health</h3>
                <div class="stat-item">
                    <span class="stat-label">Database Status</span>
                    <span style="color: #51cf66;">Connected</span>
                </div>
                <div class="stat-item">
                    <span class="stat-label">Active Triggers</span>
                    <span style="color: #ffd43b;">3 Active</span>
                </div>
                <div class="stat-item">
                    <span class="stat-label">Total Users</span>
                    <span class="stat-value">${totalUsers}</span>
                </div>
                <div class="stat-item">
                    <span class="stat-label">Total Logs</span>
                    <span class="stat-value">${totalLogs}</span>
                </div>
            </div>
        </div>
        
        <footer class="footer">
            <p>© 2026 Automated Logging System - Analytics Dashboard</p>
        </footer>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>