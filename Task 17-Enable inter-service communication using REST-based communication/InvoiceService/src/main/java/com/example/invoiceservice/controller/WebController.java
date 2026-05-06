package com.example.invoiceservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.invoiceservice.client.CustomerClient;
import com.example.invoiceservice.model.Customer;
import com.example.invoiceservice.model.Invoice;
import com.example.invoiceservice.service.InvoiceService;

@Controller
public class WebController {
    
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    
    private final InvoiceService invoiceService;
    private final CustomerClient customerClient;
    
    public WebController(InvoiceService invoiceService, CustomerClient customerClient) {
        this.invoiceService = invoiceService;
        this.customerClient = customerClient;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        logger.info("Loading home page");
        model.addAttribute("pageTitle", "Invoice Management System");
        return "home";
    }
    
    @GetMapping("/invoices")
    public String listInvoices(Model model) {
        logger.info("Loading invoices page");
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);
        model.addAttribute("pageTitle", "All Invoices");
        return "invoices";
    }
    
    @GetMapping("/customers")
    public String listCustomers(Model model) {
        logger.info("Loading customers page");
        List<Customer> customers = customerClient.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("pageTitle", "All Customers");
        
        if (customers.isEmpty()) {
            model.addAttribute("error", "Unable to fetch customers. Customer service may be down.");
        }
        
        return "customers";
    }
    
    @GetMapping("/invoices/customer/{customerId}")
    public String customerInvoices(@PathVariable Long customerId, Model model) {
        logger.info("Loading invoices for customer: {}", customerId);
        
        List<Invoice> invoices = invoiceService.getInvoicesByCustomerId(customerId);
        Customer customer = customerClient.getCustomerById(customerId);
        
        model.addAttribute("invoices", invoices);
        model.addAttribute("customer", customer);
        model.addAttribute("pageTitle", "Invoices for Customer");
        
        if (invoices.isEmpty()) {
            model.addAttribute("message", "No invoices found for this customer");
        }
        
        return "customer-invoices";
    }
    
    @GetMapping("/create-invoice")
    public String showCreateInvoiceForm(Model model) {
        logger.info("Loading create invoice form");
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("customers", customerClient.getAllCustomers());
        model.addAttribute("pageTitle", "Create Invoice");
        return "create-invoice";
    }
    
    @PostMapping("/create-invoice")
    public String createInvoice(@ModelAttribute Invoice invoice, Model model) {
        logger.info("Creating invoice via web form");
        
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        
        if (createdInvoice != null) {
            model.addAttribute("success", "Invoice created successfully! Number: " + 
                createdInvoice.getInvoiceNumber());
            return "redirect:/invoices";
        } else {
            model.addAttribute("error", "Failed to create invoice. Customer not found!");
            model.addAttribute("customers", customerClient.getAllCustomers());
            return "create-invoice";
        }
    }
}