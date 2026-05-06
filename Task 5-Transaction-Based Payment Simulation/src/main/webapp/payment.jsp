<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.payment.model.User" %>
<%@ page import="com.payment.model.Merchant" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Process Payment</title>
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
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .payment-container {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
            border: 1px solid rgba(255, 255, 255, 0.1);
            width: 90%;
            max-width: 600px;
        }
        
        h2 {
            color: #4ecdc4;
            text-align: center;
            margin-bottom: 30px;
            font-size: 2em;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            color: #a8dadc;
            margin-bottom: 8px;
            font-weight: 500;
        }
        
        select, input {
            width: 100%;
            padding: 12px 15px;
            background: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 8px;
            color: #f1faee;
            font-size: 1em;
            transition: all 0.3s ease;
        }
        
        select option {
            background: #1a1a2e;
            color: #f1faee;
        }
        
        select:focus, input:focus {
            outline: none;
            border-color: #4ecdc4;
            box-shadow: 0 0 10px rgba(78, 205, 196, 0.3);
        }
        
        .balance-info {
            background: rgba(78, 205, 196, 0.1);
            border-left: 4px solid #4ecdc4;
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
        }
        
        .balance-info p {
            color: #f1faee;
            margin: 5px 0;
        }
        
        .balance-info span {
            color: #4ecdc4;
            font-weight: bold;
            font-size: 1.2em;
        }
        
        button {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #4ecdc4 0%, #2c7a7b 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 1.2em;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 20px;
        }
        
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(78, 205, 196, 0.4);
        }
        
        .info-text {
            color: #a8dadc;
            font-size: 0.9em;
            margin-top: 5px;
        }
        
        .merchant-list {
            margin-top: 30px;
        }
        
        .merchant-item {
            background: rgba(255, 255, 255, 0.05);
            padding: 10px;
            margin: 5px 0;
            border-radius: 5px;
            color: #f1faee;
            display: flex;
            justify-content: space-between;
        }
        
        .merchant-name {
            color: #4ecdc4;
        }
    </style>
    <script>
        function updateBalance() {
            var select = document.getElementById("userId");
            var selectedOption = select.options[select.selectedIndex];
            var balance = selectedOption.getAttribute("data-balance");
            document.getElementById("userBalance").textContent = "₹" + parseFloat(balance).toFixed(2);
        }
        
        function validateAmount() {
            var amount = document.getElementById("amount").value;
            var balanceText = document.getElementById("userBalance").textContent;
            var balance = parseFloat(balanceText.replace('₹', ''));
            var errorDiv = document.getElementById("amountError");
            
            if (amount > balance) {
                errorDiv.textContent = "Insufficient balance!";
                errorDiv.style.color = "#ff6b6b";
                return false;
            } else if (amount <= 0) {
                errorDiv.textContent = "Amount must be positive!";
                errorDiv.style.color = "#ff6b6b";
                return false;
            } else {
                errorDiv.textContent = "✓ Valid amount";
                errorDiv.style.color = "#4ecdc4";
                return true;
            }
        }
    </script>
</head>
<body>
    <div class="payment-container">
        <h2>💰 Process Payment</h2>
        
        <form action="payment" method="post" onsubmit="return validateAmount()">
            <div class="form-group">
                <label>Select User</label>
                <select name="userId" id="userId" onchange="updateBalance()" required>
                    <option value="">Choose a user</option>
                    <% 
                        List<User> users = (List<User>) request.getAttribute("users");
                        if (users != null) {
                            for (User user : users) {
                    %>
                    <option value="<%= user.getUserId() %>" data-balance="<%= user.getBalance() %>">
                        <%= user.getName() %> - <%= user.getEmail() %>
                    </option>
                    <% 
                            }
                        }
                    %>
                </select>
            </div>
            
            <div class="balance-info">
                <p>Available Balance: <span id="userBalance">₹0.00</span></p>
            </div>
            
            <div class="form-group">
                <label>Select Merchant</label>
                <select name="merchantId" required>
                    <option value="">Choose a merchant</option>
                    <% 
                        List<Merchant> merchants = (List<Merchant>) request.getAttribute("merchants");
                        if (merchants != null) {
                            for (Merchant merchant : merchants) {
                    %>
                    <option value="<%= merchant.getMerchantId() %>">
                        <%= merchant.getName() %>
                    </option>
                    <% 
                            }
                        }
                    %>
                </select>
            </div>
            
            <div class="form-group">
                <label>Amount (₹)</label>
                <input type="number" name="amount" id="amount" step="0.01" min="0.01" 
                       onkeyup="validateAmount()" required placeholder="Enter amount">
                <div id="amountError" class="info-text"></div>
            </div>
            
            <button type="submit">Process Payment</button>
        </form>
        
        <div class="merchant-list">
            <h3 style="color: #a8dadc; margin-bottom: 10px;">Registered Merchants</h3>
            <% 
                if (merchants != null) {
                    for (Merchant merchant : merchants) {
            %>
            <div class="merchant-item">
                <span class="merchant-name"><%= merchant.getName() %></span>
                <span>Balance: ₹<%= String.format("%.2f", merchant.getBalance()) %></span>
            </div>
            <% 
                    }
                }
            %>
        </div>
    </div>
</body>
</html>