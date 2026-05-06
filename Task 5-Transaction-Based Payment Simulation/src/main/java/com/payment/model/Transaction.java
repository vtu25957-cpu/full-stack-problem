package com.payment.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int userId;
    private int merchantId;
    private double amount;
    private String status;
    private Timestamp transactionDate;
    
    // Constructors
    public Transaction() {}
    
    public Transaction(int userId, int merchantId, double amount, String status) {
        this.userId = userId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.status = status;
    }
    
    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getMerchantId() { return merchantId; }
    public void setMerchantId(int merchantId) { this.merchantId = merchantId; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Timestamp getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Timestamp transactionDate) { this.transactionDate = transactionDate; }
}