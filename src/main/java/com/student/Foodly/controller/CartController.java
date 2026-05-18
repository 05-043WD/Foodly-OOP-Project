package com.student.Foodly.controller;


import com.student.Foodly.model.CartItem;
import com.student.Foodly.model.FoodItem;
import com.student.Foodly.service.FoodItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private FoodItemService foodItemService;

    // getting the cart from the session
    private List<CartItem> getSessionCart(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private double calculateTotal(List<CartItem> cart) {
        double total = 0.0;
        for (CartItem item : cart) {
            total += item.getSubTotal();
        }
        return total;
    }

    @GetMapping
    public String showCart(HttpSession session, Model model, RedirectAttributes ra) {
        if (UserController.currentUser == null) {
            ra.addFlashAttribute("errorMessage", "Please log in to view your cart.");
            return "redirect:/login";
        }
        
        List<CartItem> cart = getSessionCart(session);
        model.addAttribute("cartItems", cart);
        model.addAttribute("cartTotal", calculateTotal(cart));
        model.addAttribute("pageTitle", "Your Cart - Foodly");
        
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam String foodId, @RequestParam(defaultValue = "1") int quantity, HttpSession session, RedirectAttributes ra) {
        if (UserController.currentUser == null) {
            ra.addFlashAttribute("errorMessage", "Please log in to add items.");
            return "redirect:/login";
        }

        FoodItem food = foodItemService.findById(foodId);
        if (food == null) {
            ra.addFlashAttribute("errorMessage", "Item not found.");
            return "redirect:/";
        }

        List<CartItem> cart = getSessionCart(session);
        
        // checking if the item is already in the cart
        boolean found = false;
        for (CartItem ci : cart) {
            if (ci.getFoodId().equals(food.getId())) {
                ci.setQuantity(ci.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        // add it to the list
        if (!found) {
            cart.add(new CartItem(food.getId(), food.getName(), food.getPrice(), quantity));
        }

        ra.addFlashAttribute("successMessage", food.getName() + " added to your cart!");
        return "redirect:/"; // go back to menu
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam String foodId, @RequestParam String action, HttpSession session) {
        List<CartItem> cart = getSessionCart(session);
        
        CartItem toRemove = null;
        for (CartItem ci : cart) {
            if (ci.getFoodId().equals(foodId)) {
                if (action.equals("plus")) {
                    ci.setQuantity(ci.getQuantity() + 1);
                } else if (action.equals("minus")) {
                    ci.setQuantity(ci.getQuantity() - 1);
                    if (ci.getQuantity() <= 0) {
                        toRemove = ci;
                    }
                }
                break;
            }
        }
        
        if (toRemove != null) {
            cart.remove(toRemove);
        }
        
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model, RedirectAttributes ra) {
        if (UserController.currentUser == null) return "redirect:/login";

        List<CartItem> cart = getSessionCart(session);
        if (cart.isEmpty()) {
            ra.addFlashAttribute("errorMessage", "Your cart is empty.");
            return "redirect:/cart";
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("cartTotal", calculateTotal(cart));
        model.addAttribute("user", UserController.currentUser); // setup the user address
        model.addAttribute("pageTitle", "Checkout - Foodly");

        return "checkout";
    }

    @Autowired
    private com.student.Foodly.service.OrderService orderService;

    @PostMapping("/checkout/confirm")
    public String confirmOrder(@RequestParam String deliveryAddress, HttpSession session, RedirectAttributes ra) {
        if (UserController.currentUser == null) return "redirect:/login";

        List<CartItem> cart = getSessionCart(session);
        if (cart.isEmpty()) {
            ra.addFlashAttribute("errorMessage", "Your cart is empty.");
            return "redirect:/cart";
        }

        double total = calculateTotal(cart);
        
        // putting the item list together as text
        StringBuilder itemsSummary = new StringBuilder();
        for (int i = 0; i < cart.size(); i++) {
            CartItem ci = cart.get(i);
            itemsSummary.append(ci.getQuantity()).append("x ").append(ci.getName());
            if (i < cart.size() - 1) {
                itemsSummary.append(", ");
            }
        }

        com.student.Foodly.model.Order newOrder = new com.student.Foodly.model.Order(
            null, 
            UserController.currentUser.getName(), 
            UserController.currentUser.getEmail(), 
            deliveryAddress, 
            itemsSummary.toString(), 
            total, 
            "Pending"
        );

        orderService.createOrder(newOrder);

        // empty the cart
        cart.clear();

        ra.addFlashAttribute("successMessage", "Order Placed successfully!");
        return "redirect:/orders/history";
    }
}
