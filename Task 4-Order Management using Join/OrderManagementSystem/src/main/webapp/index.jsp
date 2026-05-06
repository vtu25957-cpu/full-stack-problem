<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.ordermanagement.model.Order, com.ordermanagement.model.Customer" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Management System</title>
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
                <a href="${pageContext.request.contextPath}/test" class="nav-link">Test</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <!-- Get data from request -->
        <% 
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders == null) orders = new ArrayList<>();
        
        Order highestOrder = (Order) request.getAttribute("highestOrder");
        Customer activeCustomer = (Customer) request.getAttribute("activeCustomer");
        %>
        
        <!-- Stats Grid -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-label">Total Orders</div>
                <div class="stat-value"><%= orders.size() %></div>
                <div>Across all customers</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-label">Highest Order Value</div>
                <div class="stat-value">
                    $<%= (highestOrder != null) ? String.format("%.2f", highestOrder.getTotalAmount()) : "0.00" %>
                </div>
                <div>
                    <%= (highestOrder != null) ? "by " + highestOrder.getCustomerName() : "" %>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="stat-label">Most Active Customer</div>
                <div class="stat-value">
                    <%= (activeCustomer != null) ? activeCustomer.getFirstName() : "N/A" %>
                </div>
                <div>
                    <%= (activeCustomer != null) ? activeCustomer.getEmail() : "" %>
                </div>
            </div>
        </div>
        
        <!-- Main Content Card -->
        <div class="card">
            <div class="card-header">
                <span>📋 All Orders</span>
                <span style="float: right; font-size: 0.9rem;">
                    Last Updated: <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) %>
                </span>
            </div>
            
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Customer</th>
                            <th>Product</th>
                            <th>Order Date</th>
                            <th>Quantity</th>
                            <th>Total Amount</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        if (orders.isEmpty()) {
                        %>
                            <tr>
                                <td colspan="8" style="text-align: center; padding: 30px;">
                                    No orders found. <a href="${pageContext.request.contextPath}/orders">Refresh</a>
                                </td>
                            </tr>
                        <% 
                        } else {
                            for (Order order : orders) {
                        %>
                            <tr>
                                <td>#<%= order.getOrderId() %></td>
                                <td><%= order.getCustomerName() %></td>
                                <td><%= order.getProductName() %></td>
                                <td><%= order.getOrderDate() %></td>
                                <td><%= order.getQuantity() %></td>
                                <td>$<%= String.format("%.2f", order.getTotalAmount()) %></td>
                                <td>
                                    <span class="status-badge status-<%= order.getOrderStatus().toLowerCase() %>">
                                        <%= order.getOrderStatus() %>
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/orders?action=customer&id=<%= order.getCustomerId() %>" 
                                       class="btn btn-primary">View Customer</a>
                                </td>
                            </tr>
                        <% 
                            }
                        } 
                        %>
                    </tbody>
                </table>
            </div>
        </div>
        
        <!-- Highest Order Details (if requested) -->
        <% if (highestOrder != null) { %>
        <div class="card" style="margin-top: 20px;">
            <div class="card-header">🏆 Highest Value Order Details</div>
            <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 20px;">
                <div>
                    <strong>Order ID:</strong> #<%= highestOrder.getOrderId() %><br>
                    <strong>Customer:</strong> <%= highestOrder.getCustomerName() %><br>
                    <strong>Product:</strong> <%= highestOrder.getProductName() %>
                </div>
                <div>
                    <strong>Amount:</strong> $<%= String.format("%.2f", highestOrder.getTotalAmount()) %><br>
                    <strong>Quantity:</strong> <%= highestOrder.getQuantity() %><br>
                    <strong>Status:</strong> 
                    <span class="status-badge status-<%= highestOrder.getOrderStatus().toLowerCase() %>">
                        <%= highestOrder.getOrderStatus() %>
                    </span>
                </div>
            </div>
        </div>
        <% } %>
        
        <!-- Most Active Customer Details (if requested) -->
        <% if (activeCustomer != null) { %>
        <div class="card" style="margin-top: 20px;">
            <div class="card-header">🌟 Most Active Customer</div>
            <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 20px;">
                <div>
                    <strong>Name:</strong> <%= activeCustomer.getFullName() %><br>
                    <strong>Email:</strong> <%= activeCustomer.getEmail() %>
                </div>
                <div>
                    <strong>Phone:</strong> <%= activeCustomer.getPhone() != null ? activeCustomer.getPhone() : "N/A" %><br>
                    <strong>Registered:</strong> <%= activeCustomer.getRegistrationDate() != null ? activeCustomer.getRegistrationDate() : "N/A" %>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    
    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 Order Management System. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>