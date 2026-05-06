package com.dashboard.model;

public class Student {
    private int id;
    private String name;
    private String department;
    private String grade;
    private String joinDate;  // This should be String
    private String status;
    
    // Constructors
    public Student() {}
    
    public Student(int id, String name, String department, String grade, String joinDate, String status) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.grade = grade;
        this.joinDate = joinDate;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}