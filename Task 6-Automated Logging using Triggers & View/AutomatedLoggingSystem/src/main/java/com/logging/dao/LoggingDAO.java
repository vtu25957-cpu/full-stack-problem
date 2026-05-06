package com.logging.dao;

import com.logging.model.AuditLog;
import com.logging.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggingDAO {
    
    // User operations
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                users.add(user);
            }
            System.out.println("✅ Loaded " + users.size() + " users from database");
        } catch (SQLException e) {
            System.err.println("❌ Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }
    
    public boolean addUser(User user, String ipAddress) {
        String sql = "INSERT INTO users (username, email, full_name, role) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getRole());
            
            int result = pstmt.executeUpdate();
            System.out.println("✅ User added: " + user.getUsername() + ", Result: " + result);
            return result > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error adding user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteUser(int userId, String ipAddress) {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            int result = pstmt.executeUpdate();
            System.out.println("✅ User deleted: ID " + userId + ", Result: " + result);
            return result > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Audit Log operations
    public List<AuditLog> getAllLogs() {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT 1000";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setLogId(rs.getInt("log_id"));
                log.setTableName(rs.getString("table_name"));
                log.setAction(rs.getString("action"));
                log.setRecordId(rs.getInt("record_id"));
                log.setOldValue(rs.getString("old_value"));
                log.setNewValue(rs.getString("new_value"));
                log.setUsername(rs.getString("username"));
                log.setIpAddress(rs.getString("ip_address"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                logs.add(log);
            }
            System.out.println("✅ Loaded " + logs.size() + " audit logs");
        } catch (SQLException e) {
            System.err.println("❌ Error loading logs: " + e.getMessage());
            e.printStackTrace();
        }
        return logs;
    }
    
    public List<AuditLog> getRecentLogs(int limit) {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setLogId(rs.getInt("log_id"));
                log.setTableName(rs.getString("table_name"));
                log.setAction(rs.getString("action"));
                log.setRecordId(rs.getInt("record_id"));
                log.setUsername(rs.getString("username"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                logs.add(log);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error loading recent logs: " + e.getMessage());
            e.printStackTrace();
        }
        return logs;
    }
    
    // Dashboard statistics
    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("📊 Total users: " + count);
                return count;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getting total users: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTodayActions() {
        String sql = "SELECT COUNT(*) FROM audit_logs WHERE DATE(timestamp) = CURRENT_DATE";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("📊 Today's actions: " + count);
                return count;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getting today's actions: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalLogs() {
        String sql = "SELECT COUNT(*) FROM audit_logs";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("📊 Total logs: " + count);
                return count;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getting total logs: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getActiveUsers() {
        String sql = "SELECT COUNT(DISTINCT username) FROM audit_logs WHERE timestamp >= DATE_SUB(NOW(), INTERVAL 24 HOUR) AND username IS NOT NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("📊 Active users (24h): " + count);
                return count;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getting active users: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    // View data from daily_activity view
    public List<Map<String, Object>> getDailyActivity() {
        List<Map<String, Object>> activities = new ArrayList<>();
        String sql = "SELECT * FROM daily_activity ORDER BY activity_date DESC LIMIT 7";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> activity = new HashMap<>();
                activity.put("activityDate", rs.getDate("activity_date"));
                activity.put("totalActions", rs.getInt("total_actions"));
                activity.put("inserts", rs.getInt("inserts"));
                activity.put("updates", rs.getInt("updates"));
                activity.put("deletes", rs.getInt("deletes"));
                activity.put("uniqueUsers", rs.getInt("unique_users"));
                activities.add(activity);
            }
            System.out.println("📊 Loaded " + activities.size() + " daily activity records");
        } catch (SQLException e) {
            System.err.println("❌ Error loading daily activity: " + e.getMessage());
            e.printStackTrace();
        }
        return activities;
    }
    
    // Analytics data
 // Add this method to LoggingDAO.java
    public Map<String, Object> getAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        try {
            // Get total counts for percentages
            int totalInserts = 0;
            int totalUpdates = 0;
            int totalDeletes = 0;
            int maxActions = 1; // Avoid division by zero
            int maxTableActions = 1;
            
            // Most active users
            String userActivitySql = "SELECT username, COUNT(*) as action_count FROM audit_logs " +
                                     "WHERE username IS NOT NULL AND username != '' " +
                                     "GROUP BY username ORDER BY action_count DESC LIMIT 5";
            
            List<Map<String, Object>> activeUsers = new ArrayList<>();
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(userActivitySql)) {
                
                while (rs.next()) {
                    Map<String, Object> userStat = new HashMap<>();
                    userStat.put("username", rs.getString("username"));
                    int count = rs.getInt("action_count");
                    userStat.put("actionCount", count);
                    activeUsers.add(userStat);
                    
                    if (count > maxActions) {
                        maxActions = count;
                    }
                }
            } catch (SQLException e) {
                System.err.println("❌ Error getting active users analytics: " + e.getMessage());
            }
            
            // Table activity distribution
            String tableActivitySql = "SELECT table_name, COUNT(*) as action_count FROM audit_logs " +
                                      "GROUP BY table_name ORDER BY action_count DESC";
            
            List<Map<String, Object>> tableActivity = new ArrayList<>();
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(tableActivitySql)) {
                
                while (rs.next()) {
                    Map<String, Object> tableStat = new HashMap<>();
                    tableStat.put("tableName", rs.getString("table_name"));
                    int count = rs.getInt("action_count");
                    tableStat.put("actionCount", count);
                    tableActivity.add(tableStat);
                    
                    if (count > maxTableActions) {
                        maxTableActions = count;
                    }
                }
            } catch (SQLException e) {
                System.err.println("❌ Error getting table activity: " + e.getMessage());
            }
            
            // Get action distribution
            String actionCountSql = "SELECT action, COUNT(*) as count FROM audit_logs GROUP BY action";
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(actionCountSql)) {
                
                int total = 0;
                while (rs.next()) {
                    total += rs.getInt("count");
                }
                
                // Reset cursor to get individual counts
                rs.beforeFirst();
                while (rs.next()) {
                    String action = rs.getString("action");
                    int count = rs.getInt("count");
                    if ("INSERT".equals(action)) {
                        totalInserts = count;
                    } else if ("UPDATE".equals(action)) {
                        totalUpdates = count;
                    } else if ("DELETE".equals(action)) {
                        totalDeletes = count;
                    }
                }
                
                // Calculate percentages
                if (total > 0) {
                    analytics.put("insertPercent", (totalInserts * 100) / total);
                    analytics.put("updatePercent", (totalUpdates * 100) / total);
                    analytics.put("deletePercent", (totalDeletes * 100) / total);
                } else {
                    analytics.put("insertPercent", 33);
                    analytics.put("updatePercent", 33);
                    analytics.put("deletePercent", 34);
                }
                
            } catch (SQLException e) {
                System.err.println("❌ Error getting action distribution: " + e.getMessage());
            }
            
            analytics.put("activeUsers", activeUsers);
            analytics.put("tableActivity", tableActivity);
            analytics.put("maxActions", maxActions);
            analytics.put("maxTableActions", maxTableActions);
            
            System.out.println("✅ Analytics loaded: " + activeUsers.size() + " users, " + tableActivity.size() + " tables");
            
        } catch (Exception e) {
            System.err.println("❌ Error in getAnalytics: " + e.getMessage());
            e.printStackTrace();
            
            // Provide default values to avoid JSP errors
            analytics.put("activeUsers", new ArrayList<>());
            analytics.put("tableActivity", new ArrayList<>());
            analytics.put("maxActions", 1);
            analytics.put("maxTableActions", 1);
            analytics.put("insertPercent", 33);
            analytics.put("updatePercent", 33);
            analytics.put("deletePercent", 34);
        }
        
        return analytics;
    }
}
