<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Failed</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .failure-container {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
            border: 1px solid rgba(255, 255, 255, 0.1);
            width: 90%;
            max-width: 500px;
            text-align: center;
        }
        
        .failure-icon {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #ff6b6b 0%, #c92a2a 100%);
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto 30px;
            font-size: 40px;
            color: white;
        }
        
        h1 {
            color: #ff6b6b;
            margin-bottom: 20px;
            font-size: 2em;
        }
        
        .error-message {
            background: rgba(255, 107, 107, 0.1);
            border-left: 4px solid #ff6b6b;
            padding: 20px;
            margin: 30px 0;
            border-radius: 5px;
            color: #f1faee;
            font-size: 1.1em;
        }
        
        .transaction-details {
            background: rgba(255, 255, 255, 0.1);
            border-radius: 10px;
            padding: 20px;
            margin: 30px 0;
        }
        
        .detail-row {
            padding: 10px 0;
            color: #f1faee;
        }
        
        .rollback-info {
            background: rgba(255, 107, 107, 0.2);
            padding: 15px;
            border-radius: 5px;
            color: #ff6b6b;
            font-family: monospace;
            margin: 20px 0;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 30px;
            background: linear-gradient(135deg, #4ecdc4 0%, #2c7a7b 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: all 0.3s ease;
            border: 1px solid rgba(255, 255, 255, 0.2);
            margin: 10px;
        }
        
        .btn.secondary {
            background: linear-gradient(135deg, #ff6b6b 0%, #c92a2a 100%);
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(78, 205, 196, 0.4);
        }
        
        .note {
            color: #a8dadc;
            font-size: 0.9em;
            margin-top: 20px;
            padding: 10px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 5px;
        }
        
        .note span {
            color: #ff6b6b;
            font-weight: bold;
        }
        
        .suggestions {
            text-align: left;
            margin-top: 20px;
            color: #f1faee;
        }
        
        .suggestions li {
            padding: 5px 0;
            color: #a8dadc;
        }
    </style>
</head>
<body>
    <div class="failure-container">
        <div class="failure-icon">✗</div>
        <h1>Transaction Failed</h1>
        
        <div class="error-message">
            <%= request.getAttribute("error") != null ? request.getAttribute("error") : "Unable to process payment" %>
        </div>
        
        <div class="transaction-details">
            <div class="detail-row">
                <strong>Transaction Status:</strong> Failed
            </div>
            <div class="detail-row">
                <strong>Time:</strong> <%= new java.util.Date() %>
            </div>
        </div>
        
        <div class="rollback-info">
            ⚠️ ROLLBACK executed - No changes were made to any accounts
        </div>
        
        <div class="note">
            <span>✓ Transaction rolled back</span> - Database consistency maintained
        </div>
        
        <div class="suggestions">
            <h3 style="color: #a8dadc; margin-bottom: 10px;">Suggestions:</h3>
            <ul style="list-style: none;">
                <li>• Check if you have sufficient balance</li>
                <li>• Verify the amount is valid</li>
                <li>• Ensure both accounts are active</li>
                <li>• Try again with a different amount</li>
            </ul>
        </div>
        
        <div>
            <a href="payment" class="btn">Try Again</a>
            <a href="index.jsp" class="btn secondary">Home</a>
        </div>
    </div>
</body>
</html>