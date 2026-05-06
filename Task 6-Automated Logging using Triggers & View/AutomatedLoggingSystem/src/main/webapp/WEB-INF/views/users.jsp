<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management - Automated Logging System</title>
    <style>
        /* Copy all styles from index.jsp here */
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
        .form-container { background-color: #252b33; padding: 2rem; border-radius: 10px; border: 1px solid #404854; max-width: 500px; margin: 2rem auto; }
        .form-group { margin-bottom: 1.5rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; color: #4a9eff; font-weight: 500; }
        .form-group input, .form-group select { width: 100%; padding: 0.8rem; background-color: #2d343d; border: 1px solid #404854; border-radius: 6px; color: #e0e0e0; }
        .form-group input:focus { outline: none; border-color: #4a9eff; }
        .btn { display: inline-block; padding: 0.6rem 1.2rem; border: none; border-radius: 6px; cursor: pointer; text-decoration: none; margin-right: 0.5rem; }
        .btn-primary { background-color: #4a9eff; color: white; }
        .btn-success { background-color: #51cf66; color: white; }
        .btn-danger { background-color: #ff6b6b; color: white; }
        .btn-sm { padding: 0.3rem 0.8rem; font-size: 0.8rem; }
        .data-table { width: 100%; background-color: #252b33; border-radius: 8px; border: 1px solid #404854; border-collapse: collapse; margin-top: 1rem; }
        .data-table thead { background-color: #2d343d; }
        .data-table th { padding: 1rem; text-align: left; color: #4a9eff; border-bottom: 2px solid #404854; }
        .data-table td { padding: 0.75rem 1rem; border-top: 1px solid #404854; color: #b0b0b0; }
        .data-table tbody tr:hover { background-color: #3a424d; }
        .role-ADMIN { color: #ffd43b !important; font-weight: bold; }
        .role-MANAGER { color: #4a9eff !important; font-weight: bold; }
        .role-USER { color: #b0b0b0 !important; }
        .role-AUDITOR { color: #51cf66 !important; font-weight: bold; }
        .footer { background-color: #252b33; padding: 1.5rem; border-radius: 8px; margin-top: 2rem; text-align: center; border: 1px solid #404854; color: #b0b0b0; }
    </style>
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>👥 User Management</h1>
            <p>Manage users with automated audit logging</p>
        </header>
        
        <nav class="nav-bar">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/users" class="active">User Management</a></li>
                <li><a href="${pageContext.request.contextPath}/logs">Audit Logs</a></li>
                <li><a href="${pageContext.request.contextPath}/reports">Daily Reports</a></li>
                <li><a href="${pageContext.request.contextPath}/analytics">Analytics</a></li>
            </ul>
        </nav>
        
        <!-- Add User Form -->
        <div class="form-container">
            <h3 style="color: #4a9eff; margin-bottom: 1.5rem;">Add New User</h3>
            <form action="${pageContext.request.contextPath}/users" method="post">
                <input type="hidden" name="action" value="add">
                
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" required>
                </div>
                
                <div class="form-group">
                    <label for="role">Role</label>
                    <select id="role" name="role">
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                        <option value="MANAGER">Manager</option>
                        <option value="AUDITOR">Auditor</option>
                    </select>
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%;">Add User</button>
            </form>
        </div>
        
        <!-- Users Table -->
        <div style="margin-top: 2rem;">
            <h2 style="color: #4a9eff; margin-bottom: 1rem;">📋 Registered Users</h2>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Full Name</th>
                        <th>Role</th>
                        <th>Created At</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>${user.fullName}</td>
                            <td><span class="role-${user.role}">${user.role}</span></td>
                            <td>${user.formattedCreatedAt}</td>
                            <td>
                                <button class="btn btn-success btn-sm" onclick="editUser(${user.id})">Edit</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <footer class="footer">
            <p>© 2026 Automated Logging System - Enterprise Audit Trail Solution</p>
        </footer>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        function editUser(id) {
            window.location.href = '${pageContext.request.contextPath}/users?action=edit&id=' + id;
        }
        function deleteUser(id) {
            if(confirm('Are you sure? This action will be logged.')) {
                window.location.href = '${pageContext.request.contextPath}/users?action=delete&id=' + id;
            }
        }
    </script>
</body>
</html>