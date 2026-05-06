package com.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductManagementApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ProductManagementApiApplication.class, args);
        System.out.println("\n🚀 Product Management API is running!");
        System.out.println("📝 API Documentation: http://localhost:8080");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("🔧 JDBC URL: jdbc:h2:mem:productdb");
        System.out.println("👤 Username: sa");
        System.out.println("🔑 Password: (leave blank)\n");
    }
}