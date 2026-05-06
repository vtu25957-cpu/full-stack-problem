<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Automated Logging System</title>
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
            min-height: 100vh;
        }
        
        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
        }
        
        /* Header */
        .header {
            background: linear-gradient(135deg, #252b33, #2d343d);
            padding: 1.5rem;
            border-radius: 12px;
            margin-bottom: 2rem;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
            border: 1px solid #404854;
        }
        
        .header h1 {
            color: #4a9eff;
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
            text-shadow: 0 2px 4px rgba(0,0,0,0.3);
        }
        
        .header p {
            color: #b0b0b0;
            font-size: 1.1rem;
        }
        
        /* Navigation */
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
            transition: all 0.3s ease;
            font-weight: 500;
        }
        
        .nav-bar a:hover {
            background-color: #3a424d;
            color: #4a9eff;
        }
        
        .nav-bar a.active {
            background-color: #4a9eff;
            color: white;
        }
        
        /* Stats Grid */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 1rem;
            margin-bottom: 2rem;
        }
        
        .stat-card {
            background-color: #252b33;
            padding: 1.5rem;
            border-radius: 8px;
            text-align: center;
            border: 1px solid #404854;
            transition: transform 0.3s ease;
        }
        
        .stat-card:hover {
            transform: translateY(-5px);
            background-color: #3a424d;
        }
        
        .stat-value {
            font-size: 2.5rem;
            font-weight: bold;
            color: #4a9eff;
            margin-bottom: 0.5rem;
        }
        
        .stat-label {
            color: #b0b0b0;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        
        /* Dashboard Grid */
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }
        
        /* Cards */
        .card {
            background-color: #252b33;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
            border: 1px solid #404854;
            transition: transform 0.3s ease;
        }
        
        .card:hover {
            transform: translateY(-5px);
            background-color: #3a424d;
        }
        
        .card h3 {
            color: #4a9eff;
            margin-bottom: 1rem;
            font-size: 1.3rem;
            border-bottom: 2px solid #404854;
            padding-bottom: 0.5rem;
        }
        
        /* Buttons */
        .btn {
            display: inline-block;
            padding: 0.6rem 1.2rem;
            border: none;
            border-radius: 6px;
            font-size: 0.9rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            text-align: center;
            margin-right: 0.5rem;
            margin-bottom: 0.5rem;
        }
        
        .btn-primary {
            background-color: #4a9eff;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #3d8aff;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(74, 158, 255, 0.3);
        }
        
        .btn-success {
            background-color: #51cf66;
            color: white;
        }
        
        .btn-success:hover {
            background-color: #45b058;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(81, 207, 102, 0.3);
        }
        
        .btn-warning {
            background-color: #ffd43b;
            color: #1a1e24;
        }
        
        .btn-warning:hover {
            background-color: #f5c534;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(255, 212, 59, 0.3);
        }
        
        .btn-danger {
            background-color: #ff6b6b;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #ff5252;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(255, 107, 107, 0.3);
        }
        
        .btn-sm {
            padding: 0.3rem 0.8rem;
            font-size: 0.8rem;
        }
        
        /* Log Viewer */
        .log-viewer {
            background-color: #2d343d;
            border-radius: 8px;
            padding: 1rem;
            font-family: 'Courier New', monospace;
            max-height: 250px;
            overflow-y: auto;
            border: 1px solid #404854;
        }
        
        .log-entry {
            padding: 0.5rem;
            border-bottom: 1px solid #404854;
            color: #b0b0b0;
            font-size: 0.9rem;
        }
        
        .log-entry:last-child {
            border-bottom: none;
        }
        
        .log-entry .timestamp {
            color: #4a9eff;
            margin-right: 1rem;
            font-family: monospace;
        }
        
        .log-entry .action {
            color: #51cf66;
            margin-right: 1rem;
            font-weight: bold;
        }
        
        /* Tables */
        .data-table {
            width: 100%;
            background-color: #252b33;
            border-radius: 8px;
            overflow: hidden;
            border: 1px solid #404854;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        
        .data-table thead {
            background-color: #2d343d;
        }
        
        .data-table th {
            padding: 1rem;
            text-align: left;
            color: #4a9eff;
            font-weight: 600;
            border-bottom: 2px solid #404854;
        }
        
        .data-table td {
            padding: 0.75rem 1rem;
            border-top: 1px solid #404854;
            color: #b0b0b0;
        }
        
        .data-table tbody tr:hover {
            background-color: #3a424d;
        }
        
        /* Forms */
        .form-container {
            background-color: #252b33;
            padding: 2rem;
            border-radius: 10px;
            border: 1px solid #404854;
            max-width: 500px;
            margin: 2rem auto;
        }
        
        .form-group {
            margin-bottom: 1.5rem;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #4a9eff;
            font-weight: 500;
        }
        
        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 0.8rem;
            background-color: #2d343d;
            border: 1px solid #404854;
            border-radius: 6px;
            color: #e0e0e0;
            font-size: 1rem;
        }
        
        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #4a9eff;
        }
        
        /* Role Colors */
        .role-ADMIN {
            color: #ffd43b !important;
            font-weight: bold;
        }
        
        .role-MANAGER {
            color: #4a9eff !important;
            font-weight: bold;
        }
        
        .role-USER {
            color: #b0b0b0 !important;
        }
        
        .role-AUDITOR {
            color: #51cf66 !important;
            font-weight: bold;
        }
        
        /* Action Colors */
        .action-INSERT {
            color: #51cf66 !important;
            font-weight: bold;
        }
        
        .action-UPDATE {
            color: #ffd43b !important;
            font-weight: bold;
        }
        
        .action-DELETE {
            color: #ff6b6b !important;
            font-weight: bold;
        }
        
        /* Search and Filter */
        .search-box {
            background-color: #2d343d;
            border: 1px solid #404854;
            border-radius: 6px;
            padding: 0.8rem;
            color: #e0e0e0;
            width: 100%;
        }
        
        .filter-select {
            background-color: #2d343d;
            border: 1px solid #404854;
            border-radius: 6px;
            padding: 0.8rem;
            color: #e0e0e0;
        }
        
        /* Modal */
        .modal {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: #252b33;
            padding: 2rem;
            border-radius: 12px;
            border: 1px solid #404854;
            z-index: 1000;
            max-width: 80%;
            max-height: 80%;
            overflow: auto;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
        }
        
        .modal h3 {
            color: #4a9eff;
            margin-bottom: 1rem;
        }
        
        .modal pre {
            background: #2d343d;
            padding: 1rem;
            border-radius: 6px;
            border: 1px solid #404854;
            color: #e0e0e0;
            white-space: pre-wrap;
            margin: 1rem 0;
        }
        
        /* Debug Message */
        .debug-message {
            background: rgba(255, 107, 107, 0.1);
            border: 1px solid #ff6b6b;
            color: #ff6b6b;
            padding: 1rem;
            border-radius: 6px;
            margin-bottom: 1.5rem;
            text-align: center;
        }
        
        /* Footer */
        .footer {
            background-color: #252b33;
            padding: 1.5rem;
            border-radius: 8px;
            margin-top: 2rem;
            text-align: center;
            border: 1px solid #404854;
            color: #b0b0b0;
        }
        
        /* Flex utilities */
        .flex-row {
            display: flex;
            gap: 1rem;
            flex-wrap: wrap;
            margin-bottom: 1rem;
        }
        
        .flex-1 {
            flex: 1;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .stats-grid {
                grid-template-columns: 1fr 1fr;
            }
            
            .dashboard-grid {
                grid-template-columns: 1fr;
            }
            
            .nav-bar ul {
                flex-direction: column;
                gap: 0.5rem;
            }
            
            .flex-row {
                flex-direction: column;
            }
        }
        
        @media (max-width: 480px) {
            .stats-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>🔐 Automated Logging System</h1>
            <p>Enterprise-grade audit logging using database triggers and views</p>
        </header>
        
        <nav class="nav-bar">
            <ul>
                <li><a href="${pageContext.request.contextPath}/" class="active">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/users">User Management</a></li>
                <li><a href="${pageContext.request.contextPath}/logs">Audit Logs</a></li>
                <li><a href="${pageContext.request.contextPath}/reports">Daily Reports</a></li>
                <li><a href="${pageContext.request.contextPath}/analytics">Analytics</a></li>
            </ul>
        </nav>
        
        <!-- Debug Info -->
        <c:if test="${totalUsers == 0}">
            <div class="debug-message">
                ⚠️ Database connected but no data found. Add some users to see stats!
            </div>
        </c:if>
        
        <!-- Stats Grid -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-value">${totalUsers != null ? totalUsers : '0'}</div>
                <div class="stat-label">TOTAL USERS</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">${todayActions != null ? todayActions : '0'}</div>
                <div class="stat-label">TODAY'S ACTIONS</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">${totalLogs != null ? totalLogs : '0'}</div>
                <div class="stat-label">TOTAL LOGS</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">${activeUsers != null ? activeUsers : '0'}</div>
                <div class="stat-label">ACTIVE USERS (24H)</div>
            </div>
        </div>
        
        <!-- Dashboard Grid -->
        <div class="dashboard-grid">
            <!-- Quick Actions Card -->
            <div class="card">
                <h3>⚡ Quick Actions</h3>
                <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                    <a href="${pageContext.request.contextPath}/users" class="btn btn-success">➕ Add User</a>
                    <a href="${pageContext.request.contextPath}/logs" class="btn btn-primary">📋 View Logs</a>
                    <a href="${pageContext.request.contextPath}/reports" class="btn btn-warning">📊 View Reports</a>
                </div>
            </div>
            
            <!-- Recent Activity Card -->
            <div class="card">
                <h3>🕒 Recent Activity</h3>
                <div class="log-viewer">
                    <c:choose>
                        <c:when test="${not empty recentLogs}">
                            <c:forEach items="${recentLogs}" var="log">
                                <div class="log-entry">
                                    <span class="timestamp">${log.formattedTimestamp}</span>
                                    <span class="action">[${log.action}]</span>
                                    ${log.tableName} - Record #${log.recordId} by ${log.username}
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="log-entry">📭 No recent activity</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            <!-- System Status Card -->
            <div class="card">
                <h3>📊 System Status</h3>
                <div style="display: grid; gap: 10px;">
                    <div style="display: flex; justify-content: space-between;">
                        <span>🔵 Server:</span>
                        <span style="color: #51cf66;">Running</span>
                    </div>
                    <div style="display: flex; justify-content: space-between;">
                        <span>🟢 Database:</span>
                        <span style="color: ${totalUsers > 0 ? '#51cf66' : '#ff6b6b'}">
                            ${totalUsers > 0 ? 'Connected' : 'No Data'}
                        </span>
                    </div>
                    <div style="display: flex; justify-content: space-between;">
                        <span>🟡 Triggers:</span>
                        <span style="color: #ffd43b;">Active</span>
                    </div>
                    <div style="display: flex; justify-content: space-between;">
                        <span>📝 Audit Trail:</span>
                        <span style="color: ${totalLogs > 0 ? '#51cf66' : '#ff6b6b'}">
                            ${totalLogs > 0 ? 'Logging' : 'Waiting'}
                        </span>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Daily Activity Preview -->
        <c:if test="${not empty dailyActivity}">
            <div style="margin-top: 2rem;">
                <h2 style="color: #4a9eff; margin-bottom: 1rem;">📈 Today's Activity</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Total</th>
                            <th>Inserts</th>
                            <th>Updates</th>
                            <th>Deletes</th>
                            <th>Users</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${dailyActivity}" var="activity" end="0">
                            <tr>
                                <td>${activity.activityDate}</td>
                                <td><strong>${activity.totalActions}</strong></td>
                                <td style="color: #51cf66;">${activity.inserts}</td>
                                <td style="color: #ffd43b;">${activity.updates}</td>
                                <td style="color: #ff6b6b;">${activity.deletes}</td>
                                <td>${activity.uniqueUsers}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        
        <footer class="footer">
            <p>© 2026 Automated Logging System - Enterprise Audit Trail Solution</p>
            <p style="font-size: 0.9rem; margin-top: 0.5rem;">Powered by Database Triggers & Views</p>
        </footer>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>