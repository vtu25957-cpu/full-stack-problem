package com.payment.dao;

import java.sql.*;

public class PaymentDAO {
    
    public boolean processPayment(int userId, int merchantId, double amount) {
        Connection conn = null;
        PreparedStatement deductStmt = null;
        PreparedStatement addStmt = null;
        PreparedStatement transactionStmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Check balance
            String checkBalanceQuery = "SELECT balance FROM users WHERE user_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceQuery)) {
                checkStmt.setInt(1, userId);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (currentBalance < amount) {
                        conn.rollback();
                        return false;
                    }
                } else {
                    conn.rollback();
                    return false;
                }
            }
            
            // Deduct from user
            String deductQuery = "UPDATE users SET balance = balance - ? WHERE user_id = ?";
            deductStmt = conn.prepareStatement(deductQuery);
            deductStmt.setDouble(1, amount);
            deductStmt.setInt(2, userId);
            int deductResult = deductStmt.executeUpdate();
            
            // Add to merchant
            String addQuery = "UPDATE merchants SET balance = balance + ? WHERE merchant_id = ?";
            addStmt = conn.prepareStatement(addQuery);
            addStmt.setDouble(1, amount);
            addStmt.setInt(2, merchantId);
            int addResult = addStmt.executeUpdate();
            
            // Record transaction
            String transactionQuery = "INSERT INTO transactions (user_id, merchant_id, amount, status) VALUES (?, ?, ?, 'SUCCESS')";
            transactionStmt = conn.prepareStatement(transactionQuery);
            transactionStmt.setInt(1, userId);
            transactionStmt.setInt(2, merchantId);
            transactionStmt.setDouble(3, amount);
            int transactionResult = transactionStmt.executeUpdate();
            
            if (deductResult > 0 && addResult > 0 && transactionResult > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (deductStmt != null) deductStmt.close();
                if (addStmt != null) addStmt.close();
                if (transactionStmt != null) transactionStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}