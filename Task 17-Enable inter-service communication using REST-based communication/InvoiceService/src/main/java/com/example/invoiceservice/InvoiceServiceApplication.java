package com.example.invoiceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvoiceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InvoiceServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("INVOICE SERVICE STARTED SUCCESSFULLY!");
        System.out.println("Port: 8082");
        System.out.println("Access the application at: http://localhost:8082");
        System.out.println("========================================");
        System.out.println("Make sure Customer Service is running on port 8081");
        System.out.println("========================================");
    }
}