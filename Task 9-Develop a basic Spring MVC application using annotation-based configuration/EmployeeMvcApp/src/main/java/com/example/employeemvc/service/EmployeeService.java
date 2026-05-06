package com.example.employeemvc.service;

import com.example.employeemvc.model.Employee;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    
    private static List<Employee> employees = new ArrayList<>();
    
    // Static initializer with more realistic data
    static {
        employees.add(new Employee(1L, "John", "Doe", "john.doe@company.com", 
                                  "IT", "+1-555-0123", "2023-01-15"));
        employees.add(new Employee(2L, "Jane", "Smith", "jane.smith@company.com", 
                                  "HR", "+1-555-0124", "2023-02-20"));
        employees.add(new Employee(3L, "Michael", "Johnson", "michael.j@company.com", 
                                  "Finance", "+1-555-0125", "2023-03-10"));
        employees.add(new Employee(4L, "Emily", "Brown", "emily.brown@company.com", 
                                  "Marketing", "+1-555-0126", "2023-04-05"));
        employees.add(new Employee(5L, "David", "Wilson", "david.wilson@company.com", 
                                  "IT", "+1-555-0127", "2023-05-12"));
        employees.add(new Employee(6L, "Sarah", "Davis", "sarah.davis@company.com", 
                                  "Sales", "+1-555-0128", "2023-06-18"));
    }
    
    public List<Employee> getAllEmployees() {
        return employees;
    }
    
    public Employee getEmployeeById(Long id) {
        return employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}