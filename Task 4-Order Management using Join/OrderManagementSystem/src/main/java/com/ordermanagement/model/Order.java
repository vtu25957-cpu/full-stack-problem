package com.ordermanagement.model;

public class Order {
    private int orderId;
    private int customerId;
    private int productId;
    private String orderDate;
    private int quantity;
    private double totalAmount;
    private String orderStatus;
    
    // Additional fields for joined data
    private String customerName;
    private String productName;
    
    public Order() {}
    
    public Order(int orderId, int customerId, int productId, String orderDate, 
                int quantity, double totalAmount, String orderStatus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }
    
    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", customer=" + customerName + 
               ", product=" + productName + ", amount=" + totalAmount + '}';
    }
}