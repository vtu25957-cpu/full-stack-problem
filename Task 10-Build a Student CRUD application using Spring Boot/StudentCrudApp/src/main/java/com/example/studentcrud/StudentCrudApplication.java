package com.example.studentcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentCrudApplication.class, args);
        System.out.println("========================================");
        System.out.println("Student CRUD Application Started!");
        System.out.println("Access the application at: http://localhost:8080");
        System.out.println("========================================");
    }
}