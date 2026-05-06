package com.ordermanagement.servlet;

import com.ordermanagement.dao.OrderDAO;
import com.ordermanagement.model.Order;
import com.ordermanagement.model.Customer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    
    @Override
    public void init() {
        orderDAO = new OrderDAO();
        System.out.println("OrderServlet initialized successfully!");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        System.out.println("Action requested: " + action);
        
        try {
            if (action == null || action.equals("list")) {
                listAllOrders(request, response);
            } else {
                switch (action) {
                    case "customer":
                        showCustomerOrders(request, response);
                        break;
                    case "highest":
                        showHighestOrder(request, response);
                        break;
                    case "active":
                        showMostActiveCustomer(request, response);
                        break;
                    default:
                        listAllOrders(request, response);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    private void listAllOrders(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    private void showCustomerOrders(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String customerIdParam = request.getParameter("id");
        if (customerIdParam == null || customerIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required");
            return;
        }
        
        int customerId = Integer.parseInt(customerIdParam);
        List<Order> orders = orderDAO.getCustomerOrderHistory(customerId);
        request.setAttribute("orders", orders);
        request.setAttribute("customerId", customerId);
        request.getRequestDispatcher("/customer-orders.jsp").forward(request, response);
    }
    
    private void showHighestOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Order highestOrder = orderDAO.getHighestValueOrder();
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.setAttribute("highestOrder", highestOrder);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    private void showMostActiveCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Customer activeCustomer = orderDAO.getMostActiveCustomer();
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.setAttribute("activeCustomer", activeCustomer);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}