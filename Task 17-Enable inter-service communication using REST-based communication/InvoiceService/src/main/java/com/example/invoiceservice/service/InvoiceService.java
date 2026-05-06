package com.example.invoiceservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.invoiceservice.client.CustomerClient;
import com.example.invoiceservice.model.Customer;
import com.example.invoiceservice.model.Invoice;

@Service
public class InvoiceService {
    
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    
    private List<Invoice> invoices = new ArrayList<>();
    private Long nextId = 1L;
    private final CustomerClient customerClient;
    
    public InvoiceService(CustomerClient customerClient) {
        this.customerClient = customerClient;
        
        // Sample invoices
        Invoice inv1 = new Invoice();
        inv1.setId(nextId++);
        inv1.setCustomerId(1L);
        inv1.setInvoiceNumber("INV-2024-001");
        inv1.setAmount(1500.00);
        inv1.setDescription("Web Development Services");
        inv1.setStatus("PAID");
        inv1.setIssueDate(new Date());
        inv1.setDueDate(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
        
        Invoice inv2 = new Invoice();
        inv2.setId(nextId++);
        inv2.setCustomerId(1L);
        inv2.setInvoiceNumber("INV-2024-002");
        inv2.setAmount(750.00);
        inv2.setDescription("Monthly Maintenance");
        inv2.setStatus("PENDING");
        inv2.setIssueDate(new Date());
        inv2.setDueDate(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
        
        Invoice inv3 = new Invoice();
        inv3.setId(nextId++);
        inv3.setCustomerId(2L);
        inv3.setInvoiceNumber("INV-2024-003");
        inv3.setAmount(2300.00);
        inv3.setDescription("Mobile App Development");
        inv3.setStatus("OVERDUE");
        inv3.setIssueDate(new Date(System.currentTimeMillis() - 45L * 24 * 60 * 60 * 1000));
        inv3.setDueDate(new Date(System.currentTimeMillis() - 15L * 24 * 60 * 60 * 1000));
        
        invoices.add(inv1);
        invoices.add(inv2);
        invoices.add(inv3);
    }
    
    public List<Invoice> getAllInvoices() {
        logger.info("Fetching all invoices");
        return invoices;
    }
    
    public Invoice getInvoiceById(Long id) {
        logger.info("Fetching invoice with ID: {}", id);
        for (Invoice invoice : invoices) {
            if (invoice.getId().equals(id)) {
                return invoice;
            }
        }
        return null;
    }
    
    public List<Invoice> getInvoicesByCustomerId(Long customerId) {
        logger.info("Fetching invoices for customer ID: {}", customerId);
        
        // Verify customer exists using REST call
        Customer customer = customerClient.getCustomerById(customerId);
        if (customer == null) {
            logger.warn("Customer with ID {} not found", customerId);
            return new ArrayList<>();
        }
        
        List<Invoice> customerInvoices = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (invoice.getCustomerId().equals(customerId)) {
                customerInvoices.add(invoice);
            }
        }
        
        return customerInvoices;
    }
    
    public Invoice createInvoice(Invoice invoice) {
        logger.info("Creating invoice for customer ID: {}", invoice.getCustomerId());
        
        // Verify customer exists before creating invoice
        Customer customer = customerClient.getCustomerById(invoice.getCustomerId());
        if (customer == null) {
            logger.error("Cannot create invoice: Customer with ID {} not found", invoice.getCustomerId());
            return null;
        }
        
        invoice.setId(nextId++);
        invoice.setInvoiceNumber("INV-2024-" + String.format("%03d", nextId));
        invoice.setStatus("PENDING");
        invoice.setIssueDate(new Date());
        invoice.setDueDate(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
        invoice.setPaidDate(null);
        invoices.add(invoice);
        logger.info("Successfully created invoice: {}", invoice.getInvoiceNumber());
        return invoice;
    }
    
    public Invoice markAsPaid(Long id) {
        Invoice invoice = getInvoiceById(id);
        
        if (invoice != null) {
            invoice.setStatus("PAID");
            invoice.setPaidDate(new Date());
            logger.info("Invoice {} marked as paid", id);
            return invoice;
        }
        
        logger.warn("Invoice with ID {} not found", id);
        return null;
    }
}