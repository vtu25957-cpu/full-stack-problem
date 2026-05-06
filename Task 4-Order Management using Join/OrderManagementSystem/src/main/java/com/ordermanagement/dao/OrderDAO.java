package com.ordermanagement.dao;

import com.ordermanagement.model.Order;
import com.ordermanagement.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    // Get customer order history with JOIN
    public List<Order> getCustomerOrderHistory(int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.*, c.first_name, c.last_name, p.product_name " +
                      "FROM orders o " +
                      "JOIN customers c ON o.customer_id = c.customer_id " +
                      "JOIN products p ON o.product_id = p.product_id " +
                      "WHERE o.customer_id = ? " +
                      "ORDER BY o.order_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = extractOrderFromResultSet(rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    // Get all orders with customer and product details
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.*, c.first_name, c.last_name, p.product_name " +
                      "FROM orders o " +
                      "JOIN customers c ON o.customer_id = c.customer_id " +
                      "JOIN products p ON o.product_id = p.product_id " +
                      "ORDER BY o.order_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Order order = extractOrderFromResultSet(rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    // Find highest value order using subquery
    public Order getHighestValueOrder() {
        Order order = null;
        String query = "SELECT o.*, c.first_name, c.last_name, p.product_name " +
                      "FROM orders o " +
                      "JOIN customers c ON o.customer_id = c.customer_id " +
                      "JOIN products p ON o.product_id = p.product_id " +
                      "WHERE o.total_amount = (SELECT MAX(total_amount) FROM orders)";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                order = extractOrderFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
    
    // Find most active customer using subquery
    public Customer getMostActiveCustomer() {
        Customer customer = null;
        String query = "SELECT c.*, COUNT(o.order_id) as order_count " +
                      "FROM customers c " +
                      "LEFT JOIN orders o ON c.customer_id = o.customer_id " +
                      "GROUP BY c.customer_id " +
                      "ORDER BY order_count DESC LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setRegistrationDate(rs.getString("registration_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    // Helper method to extract Order from ResultSet
    private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setCustomerId(rs.getInt("customer_id"));
        order.setProductId(rs.getInt("product_id"));
        order.setOrderDate(rs.getString("order_date"));
        order.setQuantity(rs.getInt("quantity"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setCustomerName(rs.getString("first_name") + " " + rs.getString("last_name"));
        order.setProductName(rs.getString("product_name"));
        return order;
    }
    
    // Additional utility methods
    public double getTotalRevenue() {
        String query = "SELECT SUM(total_amount) as total FROM orders";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    public int getOrderCount() {
        String query = "SELECT COUNT(*) as count FROM orders";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}