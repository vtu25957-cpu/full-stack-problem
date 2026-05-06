<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Status</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>REGISTRATION STATUS</h1>
            <div class="subtitle">ACADEMIC YEAR 2026</div>
        </div>

        <div class="form-container">
            <%
            String messageType = (String) request.getAttribute("messageType");
            String message = (String) request.getAttribute("message");

            if (messageType != null && messageType.equals("success")) {
            %>
                <div class="message-card success">
                    <h2>
                        <i class="fas fa-check-circle"></i>
                        SUCCESS
                    </h2>
                    <!-- FIXED: Removed the ? symbol -->
                    <p>Registration successful! Student has been added.</p>
                    <div style="margin-top: 20px; color: var(--accent-blue);">
                        <i class="fas fa-check"></i> Registration completed successfully
                    </div>
                </div>
            <% } else if (message != null) { %>
                <div class="message-card error">
                    <h2>
                        <i class="fas fa-times-circle"></i>
                        ERROR
                    </h2>
                    <p><%= message %></p>
                    <div style="margin-top: 20px; color: var(--text-dim);">
                        Please try again or contact support
                    </div>
                </div>
            <% } else { %>
                <div class="empty-state">
                    <i class="fas fa-inbox"></i>
                    <h3>No Registration Attempt</h3>
                    <p>No registration has been attempted yet.</p>
                </div>
            <% } %>

            <div class="button-group">
                <a href="register.html" class="btn btn-primary">
                    <i class="fas fa-user-plus"></i>
                    REGISTER NEW
                </a>
                <a href="ViewStudentsServlet" class="btn btn-outline">
                    <i class="fas fa-list"></i>
                    VIEW ALL
                </a>
            </div>
        </div>
    </div>
</body>
</html>