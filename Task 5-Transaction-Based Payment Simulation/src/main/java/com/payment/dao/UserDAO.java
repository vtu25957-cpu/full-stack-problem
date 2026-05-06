package com.payment.dao;

import com.payment.model.User;
import com.payment.model.Merchant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setBalance(rs.getDouble("balance"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public List<Merchant> getAllMerchants() {
        List<Merchant> merchants = new ArrayList<>();
        String query = "SELECT * FROM merchants";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Merchant merchant = new Merchant();
                merchant.setMerchantId(rs.getInt("merchant_id"));
                merchant.setName(rs.getString("name"));
                merchant.setEmail(rs.getString("email"));
                merchant.setBalance(rs.getDouble("balance"));
                merchants.add(merchant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchants;
    }
    
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Merchant getMerchantById(int merchantId) {
        String query = "SELECT * FROM merchants WHERE merchant_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, merchantId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Merchant merchant = new Merchant();
                merchant.setMerchantId(rs.getInt("merchant_id"));
                merchant.setName(rs.getString("name"));
                merchant.setEmail(rs.getString("email"));
                merchant.setBalance(rs.getDouble("balance"));
                return merchant;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}