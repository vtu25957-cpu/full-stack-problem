package com.example.invoiceservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.invoiceservice.model.Invoice;
import com.example.invoiceservice.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoiceService invoiceService;
    
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        logger.info("GET /api/invoices - Fetching all invoices");
        return new ResponseEntity<>(invoiceService.getAllInvoices(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        logger.info("GET /api/invoices/{} - Fetching invoice", id);
        Invoice invoice = invoiceService.getInvoiceById(id);
        
        if (invoice != null) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getInvoicesByCustomerId(@PathVariable Long customerId) {
        logger.info("GET /api/invoices/customer/{} - Fetching invoices for customer", customerId);
        
        List<Invoice> invoices = invoiceService.getInvoicesByCustomerId(customerId);
        
        if (invoices.isEmpty()) {
            return new ResponseEntity<>("No invoices found for customer ID: " + customerId, 
                                       HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        logger.info("POST /api/invoices - Creating invoice for customer: {}", invoice.getCustomerId());
        
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        
        if (createdInvoice != null) {
            return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
        }
        
        return new ResponseEntity<>("Customer not found with ID: " + invoice.getCustomerId(), 
                                   HttpStatus.BAD_REQUEST);
    }
    
    @PatchMapping("/{id}/pay")
    public ResponseEntity<Invoice> markInvoiceAsPaid(@PathVariable Long id) {
        logger.info("PATCH /api/invoices/{}/pay - Marking invoice as paid", id);
        Invoice paidInvoice = invoiceService.markAsPaid(id);
        
        if (paidInvoice != null) {
            return new ResponseEntity<>(paidInvoice, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}