package com.student.Foodly.controller;

import com.student.Foodly.model.FoodItem;
import com.student.Foodly.model.Review;
import com.student.Foodly.service.FoodItemService;
import com.student.Foodly.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AdminController: full CRUD management of the food menu and review moderation.
 * All routes are prefixed with /admin.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FoodItemService foodItemService;
    
    @Autowired
    private ReviewService reviewService;

    // Helper method to check admin access (Simplicity Requirement check)
    private boolean checkAdmin(RedirectAttributes ra) {
        if (UserController.currentUser == null || !UserController.currentUser.getRole().equals("ADMIN")) {
            ra.addFlashAttribute("errorMessage", "Access Denied: You must be an Administrator.");
            return false;
        }
        return true;
    }

    /**
     * GET /admin → Dashboard with all food items and reviews.
     */
    @GetMapping
    public String dashboard(Model model, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        
        model.addAttribute("foodItems", foodItemService.readAll());
        model.addAttribute("reviews", reviewService.readAll());
        model.addAttribute("pageTitle", "Admin Dashboard – Foodly");
        return "admin/dashboard";
    }

    // ===================== CREATE =====================

    /**
     * GET /admin/add-food → Show add food item form.
     */
    @GetMapping("/add-food")
    public String showAddFoodForm(Model model, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        model.addAttribute("foodItem", new FoodItem());
        model.addAttribute("pageTitle", "Add New Food – Admin");
        return "admin/add-food";
    }

    /**
     * POST /admin/add-food → Save new food item.
     */
    @PostMapping("/add-food")
    public String addFoodItem(@ModelAttribute FoodItem foodItem, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        foodItemService.create(foodItem);
        ra.addFlashAttribute("successMessage", "Food item added successfully!");
        return "redirect:/admin";
    }

    // ===================== UPDATE =====================

    /**
     * GET /admin/edit-food/{id} → Show edit form pre-filled with current data.
     */
    @GetMapping("/edit-food/{id}")
    public String showEditFoodForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        FoodItem item = foodItemService.findById(id);
        if (item == null) {
            ra.addFlashAttribute("errorMessage", "Food item not found.");
            return "redirect:/admin";
        }
        model.addAttribute("foodItem", item);
        model.addAttribute("pageTitle", "Edit Food – Admin");
        return "admin/edit-food";
    }

    /**
     * POST /admin/edit-food → Update existing item.
     */
    @PostMapping("/edit-food")
    public String editFoodItem(@ModelAttribute FoodItem foodItem, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        foodItemService.update(foodItem);
        ra.addFlashAttribute("successMessage", "Food item updated successfully!");
        return "redirect:/admin";
    }

    // ===================== DELETE =====================

    /**
     * GET /admin/delete-food/{id} → Delete item from menu.txt and redirect.
     */
    @GetMapping("/delete-food/{id}")
    public String deleteFoodItem(@PathVariable String id, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        foodItemService.delete(id);
        ra.addFlashAttribute("successMessage", "Food item deleted successfully!");
        return "redirect:/admin";
    }

    /**
     * GET /admin/delete-review/{id} → Delete a review.
     */
    @GetMapping("/delete-review/{id}")
    public String deleteReview(@PathVariable String id, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        reviewService.delete(id);
        ra.addFlashAttribute("successMessage", "Review deleted successfully!");
        return "redirect:/admin";
    }
}
