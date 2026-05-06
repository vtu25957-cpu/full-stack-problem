package com.product.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "🚀 Product Management API is running!<br/><br/>"
                + "📋 Available Endpoints:<br/>"
                + "• GET /api/products - Get all products<br/>"
                + "• GET /api/products/{id} - Get product by ID<br/>"
                + "• POST /api/products - Create new product<br/>"
                + "• PUT /api/products/{id} - Update product<br/>"
                + "• DELETE /api/products/{id} - Delete product<br/>"
                + "• GET /api/products/category/{category} - Get products by category<br/>"
                + "• GET /api/products/low-stock?threshold=10 - Get low stock products<br/><br/>"
                + "🗄️ H2 Console: <a href='/h2-console'>http://localhost:8080/h2-console</a><br/>"
                + "📝 JDBC URL: jdbc:h2:mem:productdb<br/>"
                + "👤 Username: sa<br/>"
                + "🔑 Password: (leave blank)";
    }
}