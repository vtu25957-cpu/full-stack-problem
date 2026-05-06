package com.payment.servlet;

import com.payment.dao.PaymentDAO;
import com.payment.dao.UserDAO;
import com.payment.model.User;
import com.payment.model.Merchant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private UserDAO userDAO;
    private PaymentDAO paymentDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
        paymentDAO = new PaymentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<User> users = userDAO.getAllUsers();
        List<Merchant> merchants = userDAO.getAllMerchants();
        
        request.setAttribute("users", users);
        request.setAttribute("merchants", merchants);
        request.getRequestDispatcher("/payment.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int merchantId = Integer.parseInt(request.getParameter("merchantId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            
            // Get user and merchant details for display
            User user = userDAO.getUserById(userId);
            Merchant merchant = userDAO.getMerchantById(merchantId);
            
            // Process payment with transaction
            boolean success = paymentDAO.processPayment(userId, merchantId, amount);
            
            if (success) {
                request.setAttribute("user", user);
                request.setAttribute("merchant", merchant);
                request.setAttribute("amount", amount);
                request.getRequestDispatcher("/success.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Payment failed! Insufficient balance or invalid account.");
                request.getRequestDispatcher("/failure.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input format!");
            request.getRequestDispatcher("/failure.jsp").forward(request, response);
        }
    }
}