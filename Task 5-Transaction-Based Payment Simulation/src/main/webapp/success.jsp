<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.payment.model.User" %>
<%@ page import="com.payment.model.Merchant" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Success</title>
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
        
        .success-container {
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
        
        .success-icon {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #4ecdc4 0%, #2c7a7b 100%);
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto 30px;
            font-size: 40px;
            color: white;
            animation: pulse 2s infinite;
        }
        
        @keyframes pulse {
            0% { box-shadow: 0 0 0 0 rgba(78, 205, 196, 0.7); }
            70% { box-shadow: 0 0 0 20px rgba(78, 205, 196, 0); }
            100% { box-shadow: 0 0 0 0 rgba(78, 205, 196, 0); }
        }
        
        h1 {
            color: #4ecdc4;
            margin-bottom: 20px;
            font-size: 2em;
        }
        
        .transaction-details {
            background: rgba(255, 255, 255, 0.1);
            border-radius: 10px;
            padding: 20px;
            margin: 30px 0;
        }
        
        .detail-row {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            color: #f1faee;
        }
        
        .detail-row:last-child {
            border-bottom: none;
        }
        
        .detail-label {
            color: #a8dadc;
        }
        
        .detail-value {
            color: #4ecdc4;
            font-weight: bold;
        }
        
        .transaction-id {
            background: rgba(78, 205, 196, 0.1);
            padding: 10px;
            border-radius: 5px;
            color: #f1faee;
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
            margin-top: 20px;
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
            color: #4ecdc4;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="success-container">
        <div class="success-icon">✓</div>
        <h1>Payment Successful!</h1>
        
        <div class="transaction-details">
            <div class="detail-row">
                <span class="detail-label">Transaction ID:</span>
                <span class="detail-value">#TXN<%= System.currentTimeMillis() %></span>
            </div>
            <div class="detail-row">
                <span class="detail-label">From:</span>
                <span class="detail-value"><%= ((User)request.getAttribute("user")).getName() %></span>
            </div>
            <div class="detail-row">
                <span class="detail-label">To:</span>
                <span class="detail-value"><%= ((Merchant)request.getAttribute("merchant")).getName() %></span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Amount:</span>
                <span class="detail-value">₹<%= String.format("%.2f", request.getAttribute("amount")) %></span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Date & Time:</span>
                <span class="detail-value"><%= new java.util.Date() %></span>
            </div>
        </div>
        
        <div class="transaction-id">
            Transaction committed to database with ACID compliance
        </div>
        
        <div class="note">
            <span>✓ COMMIT executed</span> - Funds transferred atomically
        </div>
        
        <a href="payment" class="btn">Make Another Payment</a>
    </div>
</body>
</html>