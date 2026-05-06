<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.student.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Records</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>STUDENT RECORDS</h1>
            <div class="subtitle">ACADEMIC YEAR 2026</div>
        </div>

        <div class="form-container">
            <%
            List<Student> students = (List<Student>) request.getAttribute("students");
            String error = (String) request.getAttribute("error");

            if (error != null) {
            %>
                <div class="message-card error">
                    <h2><i class="fas fa-exclamation-triangle"></i> ERROR</h2>
                    <p><%= error %></p>
                </div>
            <%
            }
            %>

            <div class="stats-card">
                <h2>
                    <i class="fas fa-users"></i>
                    TOTAL STUDENTS ENROLLED
                </h2>
                <div class="stats-number">
                    <%= (students != null) ? students.size() : 0 %>
                </div>
            </div>

            <div class="table-container">
                <% if (students != null && !students.isEmpty()) { %>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>NAME</th>
                                <th>EMAIL</th>
                                <th>DATE OF BIRTH</th>
                                <th>DEPARTMENT</th>
                                <th>CONTACT</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Student s : students) { %>
                            <tr>
                                <td><span class="status-badge"><%= s.getId() %></span></td>
                                <td><strong><%= s.getName() %></strong></td>
                                <td><%= s.getEmail() %></td>
                                <td><%= s.getDob() %></td>
                                <td><span class="dept-tag"><%= s.getDepartment() %></span></td>
                                <td><%= s.getPhone() %></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="empty-state">
                        <i class="fas fa-database"></i>
                        <h3>No Records Found</h3>
                        <p>No students have been registered yet.</p>
                        <a href="register.html" class="btn btn-primary" style="margin-top: 20px;">
                            <i class="fas fa-plus-circle"></i>
                            REGISTER FIRST STUDENT
                        </a>
                    </div>
                <% } %>
            </div>

            <div class="button-group">
                <a href="register.html" class="btn btn-primary">
                    <i class="fas fa-user-plus"></i>
                    REGISTER NEW
                </a>
                <a href="ViewStudentsServlet" class="btn btn-outline">
                    <i class="fas fa-sync-alt"></i>
                    REFRESH
                </a>
            </div>
        </div>
    </div>
</body>
</html>