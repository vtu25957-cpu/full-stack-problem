<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Audit Logs - Automated Logging System</title>
    <style>
        /* Copy all base styles from users.jsp */
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
        .btn { display: inline-block; padding: 0.6rem 1.2rem; border: none; border-radius: 6px; cursor: pointer; text-decoration: none; }
        .btn-primary { background-color: #4a9eff; color: white; }
        .btn-sm { padding: 0.3rem 0.8rem; font-size: 0.8rem; }
        .data-table { width: 100%; background-color: #252b33; border-radius: 8px; border: 1px solid #404854; border-collapse: collapse; }
        .data-table thead { background-color: #2d343d; }
        .data-table th { padding: 1rem; text-align: left; color: #4a9eff; border-bottom: 2px solid #404854; }
        .data-table td { padding: 0.75rem 1rem; border-top: 1px solid #404854; color: #b0b0b0; }
        .data-table tbody tr:hover { background-color: #3a424d; }
        .footer { background-color: #252b33; padding: 1.5rem; border-radius: 8px; margin-top: 2rem; text-align: center; border: 1px solid #404854; color: #b0b0b0; }
        
        /* Log-specific styles */
        .action-INSERT { color: #51cf66 !important; font-weight: bold; }
        .action-UPDATE { color: #ffd43b !important; font-weight: bold; }
        .action-DELETE { color: #ff6b6b !important; font-weight: bold; }
        
        .filters {
            display: flex;
            gap: 1rem;
            margin-bottom: 1.5rem;
            flex-wrap: wrap;
        }
        
        .search-box {
            flex: 1;
            background-color: #2d343d;
            border: 1px solid #404854;
            border-radius: 6px;
            padding: 0.8rem;
            color: #e0e0e0;
            min-width: 200px;
        }
        
        .filter-select {
            background-color: #2d343d;
            border: 1px solid #404854;
            border-radius: 6px;
            padding: 0.8rem;
            color: #e0e0e0;
            min-width: 150px;
        }
        
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
        
        .close-btn {
            float: right;
            cursor: pointer;
            color: #ff6b6b;
            font-size: 1.5rem;
        }
        
        .close-btn:hover {
            color: #ff5252;
        }
    </style>
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>📋 Audit Logs</h1>
            <p>Real-time monitoring of all database activities</p>
        </header>
        
        <nav class="nav-bar">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/users">User Management</a></li>
                <li><a href="${pageContext.request.contextPath}/logs" class="active">Audit Logs</a></li>
                <li><a href="${pageContext.request.contextPath}/reports">Daily Reports</a></li>
                <li><a href="${pageContext.request.contextPath}/analytics">Analytics</a></li>
            </ul>
        </nav>
        
        <!-- Search and Filter -->
        <div class="filters">
            <input type="text" 
                   id="log-search" 
                   class="search-box"
                   placeholder="🔍 Search logs by username, table, action...">
            
            <select id="action-filter" class="filter-select">
                <option value="">All Actions</option>
                <option value="INSERT">INSERT</option>
                <option value="UPDATE">UPDATE</option>
                <option value="DELETE">DELETE</option>
            </select>
        </div>
        
        <!-- Logs Table -->
        <table class="data-table">
            <thead>
                <tr>
                    <th>Log ID</th>
                    <th>Timestamp</th>
                    <th>Action</th>
                    <th>Table</th>
                    <th>Record ID</th>
                    <th>Username</th>
                    <th>IP Address</th>
                    <th>Details</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${logs}" var="log">
                    <tr class="log-row" data-action="${log.action}">
                        <td>${log.logId}</td>
                        <td>${log.formattedTimestamp}</td>
                        <td><span class="action-${log.action}">${log.action}</span></td>
                        <td>${log.tableName}</td>
                        <td>${log.recordId}</td>
                        <td>${log.username}</td>
                        <td>${log.ipAddress}</td>
                        <td>
                            <button onclick="showLogDetails('${log.oldValue}', '${log.newValue}')" 
                                    class="btn btn-primary btn-sm">View</button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty logs}">
                    <tr>
                        <td colspan="8" style="text-align: center; padding: 2rem; color: #b0b0b0;">
                            No audit logs found
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <footer class="footer">
            <p>© 2026 Automated Logging System - Real-time Audit Trail</p>
        </footer>
    </div>
    
    <!-- Log Details Modal -->
    <div id="logModal" class="modal">
        <span class="close-btn" onclick="closeModal()">&times;</span>
        <h3>📝 Change Details</h3>
        <div>
            <strong style="color: #4a9eff;">Old Value:</strong>
            <pre id="oldValue"></pre>
        </div>
        <div>
            <strong style="color: #4a9eff;">New Value:</strong>
            <pre id="newValue"></pre>
        </div>
        <button onclick="closeModal()" class="btn btn-primary" style="margin-top: 1rem;">Close</button>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        function showLogDetails(oldVal, newVal) {
            document.getElementById('oldValue').textContent = oldVal || 'NULL';
            document.getElementById('newValue').textContent = newVal || 'NULL';
            document.getElementById('logModal').style.display = 'block';
        }
        
        function closeModal() {
            document.getElementById('logModal').style.display = 'none';
        }
        
        // Filter functionality
        document.getElementById('action-filter').addEventListener('change', function() {
            const filter = this.value;
            const rows = document.querySelectorAll('.log-row');
            
            rows.forEach(row => {
                if (!filter || row.dataset.action === filter) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
        
        // Search functionality
        document.getElementById('log-search').addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            const rows = document.querySelectorAll('.log-row');
            
            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                if (text.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
        
        // Close modal when clicking outside
        window.onclick = function(event) {
            const modal = document.getElementById('logModal');
            if (event.target == modal) {
                modal.style.display = 'none';
            }
        }
    </script>
</body>
</html>