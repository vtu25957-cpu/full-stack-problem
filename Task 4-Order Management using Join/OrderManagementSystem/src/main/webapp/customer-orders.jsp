<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.ordermanagement.model.Order" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Order History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar">
        <div class="container">
            <a href="${pageContext.request.contextPath}/orders" class="logo">📦 Order Management</a>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/orders" class="nav-link">Dashboard</a>
                <a href="${pageContext.request.contextPath}/orders?action=highest" class="nav-link">Highest Order</a>
                <a href="${pageContext.request.contextPath}/orders?action=active" class="nav-link">Top Customer</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <!-- Back Button -->
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary" style="margin-bottom: 20px;">
            ← Back to Dashboard
        </a>
        
        <% 
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders == null) orders = new ArrayList<>();
        
        String customerId = request.getParameter("id");
        if (customerId == null) {
            customerId = (String) request.getAttribute("customerId") != null ? 
                         request.getAttribute("customerId").toString() : "Unknown";
        }
        %>
        
        <!-- Customer Orders Card -->
        <div class="card">
            <div class="card-header">
                📋 Order History for Customer ID: <%= customerId %>
            </div>
            
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Product</th>
                            <th>Order Date</th>
                            <th>Quantity</th>
                            <th>Total Amount</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        if (orders.isEmpty()) {
                        %>
                            <tr>
                                <td colspan="6" style="text-align: center; padding: 30px;">
                                    No orders found for this customer
                                </td>
                            </tr>
                        <% 
                        } else {
                            double totalSpent = 0;
                            for (Order order : orders) {
                                totalSpent += order.getTotalAmount();
                        %>
                            <tr>
                                <td>#<%= order.getOrderId() %></td>
                                <td><%= order.getProductName() %></td>
                                <td><%= order.getOrderDate() %></td>
                                <td><%= order.getQuantity() %></td>
                                <td>$<%= String.format("%.2f", order.getTotalAmount()) %></td>
                                <td>
                                    <span class="status-badge status-<%= order.getOrderStatus().toLowerCase() %>">
                                        <%= order.getOrderStatus() %>
                                    </span>
                                </td>
                            </tr>
                        <% 
                            }
                        %>
                    </tbody>
                </table>
            </div>
            
            <!-- Customer Stats -->
            <div style="margin-top: 20px; padding: 15px; background-color: var(--bg-secondary); border-radius: 8px;">
                <h3>Customer Statistics</h3>
                <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin-top: 10px;">
                    <div>
                        <strong>Total Orders:</strong> <%= orders.size() %>
                    </div>
                    <div>
                        <strong>Total Spent:</strong> 
                        $<%= String.format("%.2f", totalSpent) %>
                    </div>
                    <div>
                        <strong>Average Order:</strong> 
                        $<%= String.format("%.2f", totalSpent / orders.size()) %>
                    </div>
                </div>
            </div>
                        <% } %>
        </div>
    </div>
    
    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 Order Management System. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>