package com.payment.model;

public class Merchant {
    private int merchantId;
    private String name;
    private String email;
    private double balance;
    
    // Constructors
    public Merchant() {}
    
    public Merchant(int merchantId, String name, String email, double balance) {
        this.merchantId = merchantId;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }
    
    // Getters and Setters
    public int getMerchantId() { return merchantId; }
    public void setMerchantId(int merchantId) { this.merchantId = merchantId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}