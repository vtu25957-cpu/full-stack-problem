<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Automated Logging System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #1a1e24;
            color: #e0e0e0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .error-container {
            max-width: 600px;
            padding: 2rem;
            text-align: center;
        }
        
        .error-card {
            background-color: #252b33;
            border-radius: 12px;
            padding: 2rem;
            border: 1px solid #404854;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
        }
        
        .error-icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }
        
        h1 {
            color: #ff6b6b;
            margin-bottom: 1rem;
        }
        
        .error-code {
            color: #4a9eff;
            font-size: 1.2rem;
            margin-bottom: 1rem;
        }
        
        .error-message {
            color: #b0b0b0;
            margin-bottom: 2rem;
            padding: 1rem;
            background-color: #2d343d;
            border-radius: 6px;
            border: 1px solid #404854;
        }
        
        .btn {
            display: inline-block;
            padding: 0.8rem 1.5rem;
            background-color: #4a9eff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: all 0.3s ease;
        }
        
        .btn:hover {
            background-color: #3d8aff;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(74, 158, 255, 0.3);
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-card">
            <div class="error-icon">❌</div>
            <h1>Error Occurred</h1>
            <div class="error-code">Status Code: ${pageContext.errorData.statusCode}</div>
            <div class="error-message">
                ${pageContext.exception.message}
                <c:if test="${empty pageContext.exception.message}">
                    An unexpected error occurred. Please try again.
                </c:if>
            </div>
            <a href="${pageContext.request.contextPath}/" class="btn">Return to Dashboard</a>
        </div>
    </div>
</body>
</html>