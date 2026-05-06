package com.example.invoiceservice.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.invoiceservice.model.Customer;

@Service
public class CustomerClient {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerClient.class);
    
    private final RestTemplate restTemplate;
    private final String customerServiceUrl;
    
    public CustomerClient(RestTemplate restTemplate, 
                          @Value("${customer.service.url}") String customerServiceUrl) {
        this.restTemplate = restTemplate;
        this.customerServiceUrl = customerServiceUrl;
    }
    
    public List<Customer> getAllCustomers() {
        try {
            logger.info("Fetching all customers from: {}/api/customers", customerServiceUrl);
            ResponseEntity<Customer[]> response = restTemplate.getForEntity(
                customerServiceUrl + "/api/customers", 
                Customer[].class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Successfully fetched {} customers", response.getBody().length);
                List<Customer> customers = new ArrayList<>();
                for (Customer customer : response.getBody()) {
                    customers.add(customer);
                }
                return customers;
            }
        } catch (Exception e) {
            logger.error("Error fetching customers: {}", e.getMessage());
        }
        
        return new ArrayList<>();
    }
    
    public Customer getCustomerById(Long id) {
        try {
            logger.info("Fetching customer with ID: {} from: {}/api/customers/{}", 
                id, customerServiceUrl, id);
            ResponseEntity<Customer> response = restTemplate.getForEntity(
                customerServiceUrl + "/api/customers/" + id, 
                Customer.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Successfully fetched customer: {}", response.getBody().getName());
                return response.getBody();
            }
        } catch (Exception e) {
            logger.error("Error fetching customer {}: {}", id, e.getMessage());
        }
        
        return null;
    }
}