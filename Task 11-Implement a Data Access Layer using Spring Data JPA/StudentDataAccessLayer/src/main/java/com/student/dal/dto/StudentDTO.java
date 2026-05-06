package com.student.dal.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class StudentDTO {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    
    @Min(16)
    @Max(100)
    private Integer age;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    private LocalDate enrollmentDate;
    private Boolean active;
    
    // Constructors
    public StudentDTO() {}
    
    public StudentDTO(Long id, String name, String email, Integer age, 
                      String department, LocalDate enrollmentDate, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.department = department;
        this.enrollmentDate = enrollmentDate;
        this.active = active;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}