package com.student.Foodly.controller;


import com.student.Foodly.model.Order;
import com.student.Foodly.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    private boolean checkLoggedIn(RedirectAttributes ra) {
        if (UserController.currentUser == null) {
            ra.addFlashAttribute("errorMessage", "Please log in to view orders.");
            return false;
        }
        return true;
    }

    private boolean checkAdmin(RedirectAttributes ra) {
        if (UserController.currentUser == null || !UserController.currentUser.getRole().equals("ADMIN")) {
            ra.addFlashAttribute("errorMessage", "Access Denied: Admin role required for this action.");
            return false;
        }
        return true;
    }

    // --- Customer Views ---
    @GetMapping("/orders/history")
    public String showCustomerOrdersHistory(Model model, RedirectAttributes ra) {
        if (!checkLoggedIn(ra)) return "redirect:/login";

        // getting only this user's orders
        List<Order> orders = orderService.getOrdersByUser(UserController.currentUser.getEmail());
        model.addAttribute("orders", orders);
        model.addAttribute("pageTitle", "My Order History");
        return "orders-history";
    }

    // --- Admin Views ---
    @GetMapping("/admin/orders")
    public String showAdminOrdersDashboard(Model model, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/login";
        
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("pageTitle", "Order Tracking - Admin");
        return "admin/orders";
    }

    @PostMapping("/admin/orders/edit")
    public String updateOrderStatus(
            @RequestParam String id,
            @RequestParam String status,
            RedirectAttributes ra) {
            
        if (!checkAdmin(ra)) return "redirect:/";

        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            existingOrder.setStatus(status);
            orderService.updateOrder(existingOrder);
            ra.addFlashAttribute("successMessage", "Order Status Updated to " + status + "!");
        } else {
            ra.addFlashAttribute("errorMessage", "Order not found.");
        }
        
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/delete/{id}")
    public String cancelOrder(@PathVariable String id, RedirectAttributes ra) {
        // make sure someone is logged in before canceling
        if (!checkLoggedIn(ra)) return "redirect:/login";
        
        orderService.deleteOrder(id);
        
        ra.addFlashAttribute("successMessage", "Order Cancelled Successfully!");
        
        if (UserController.currentUser != null && UserController.currentUser.getRole().equals("ADMIN")) {
            return "redirect:/admin/orders";
        }
        return "redirect:/orders/history";
    }
}
