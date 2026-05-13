package com.student.Foodly.controller;

// Module: Member 2 - Food Menu Management

import com.student.Foodly.model.FoodItem;
import com.student.Foodly.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * MenuController.java
 * Handles the HTTP routes and maps to Action Modals for Food Items.
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    private FoodItemService foodItemService;

    private boolean checkAdmin(RedirectAttributes ra) {
        if (UserController.currentUser == null || !UserController.currentUser.getRole().equals("ADMIN")) {
            ra.addFlashAttribute("errorMessage", "Access Denied.");
            return false;
        }
        return true;
    }

    /**
     * READ: Dashboard
     */
    @GetMapping
    public String showMenuDashboard(Model model, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        
        List<FoodItem> items = foodItemService.readAll();
        model.addAttribute("foodItems", items);
        model.addAttribute("newFood", new FoodItem()); // Embedded object for th:object binding
        model.addAttribute("pageTitle", "Food Menu - Member 2");
        return "admin/menu";
    }

    /**
     * CREATE / ADD
     */
    @PostMapping("/add")
    public String addFoodItem(@ModelAttribute("newFood") FoodItem item, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        
        foodItemService.create(item);
        
        ra.addFlashAttribute("successMessage", "Food Item Added Successfully!");
        return "redirect:/admin/menu";
    }

    /**
     * UPDATE / EDIT
     */
    @PostMapping("/edit")
    public String editFoodItem(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam String category,
            @RequestParam String imageUrl,
            @RequestParam(required = false) boolean available,
            RedirectAttributes ra) {
            
        if (!checkAdmin(ra)) return "redirect:/";

        FoodItem item = new FoodItem(id, name, description, price, category, imageUrl, available);
        foodItemService.update(item);

        ra.addFlashAttribute("successMessage", "Food Item Updated Successfully!");
        return "redirect:/admin/menu";
    }

    /**
     * DELETE
     */
    @GetMapping("/delete/{id}")
    public String deleteFoodItem(@PathVariable String id, RedirectAttributes ra) {
        System.out.println("MenuController.deleteFoodItem invoked for id: [" + id + "]");
        if (!checkAdmin(ra)) return "redirect:/";
        
        foodItemService.delete(id);
        
        System.out.println("MenuController.deleteFoodItem completed for id: [" + id + "]");
        ra.addFlashAttribute("successMessage", "Food Item Removed!");
        return "redirect:/admin/menu";
    }
}
