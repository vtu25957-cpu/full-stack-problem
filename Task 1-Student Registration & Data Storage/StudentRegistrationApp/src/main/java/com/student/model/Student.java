package com.student.model;

public class Student {
    private int id;
    private String name;
    private String email;
    private String dob;
    private String department;
    private String phone;
    
    // Default constructor
    public Student() {}
    
    // Parameterized constructor
    public Student(String name, String email, String dob, String department, String phone) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.department = department;
        this.phone = phone;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDob() {
        return dob;
    }
    
    public void setDob(String dob) {
        this.dob = dob;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}