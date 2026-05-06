package com.logging.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AuditLog {
    private int logId;
    private String tableName;
    private String action;
    private int recordId;
    private String oldValue;
    private String newValue;
    private String username;
    private LocalDateTime timestamp;
    private String ipAddress;
    
    // Constructors
    public AuditLog() {}
    
    public AuditLog(String tableName, String action, int recordId, String username) {
        this.tableName = tableName;
        this.action = action;
        this.recordId = recordId;
        this.username = username;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    
    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }
    
    public String getOldValue() { return oldValue; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }
    
    public String getNewValue() { return newValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    // Helper method for JSP
    public String getFormattedTimestamp() {
        if (timestamp != null) {
            return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return "";
    }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}