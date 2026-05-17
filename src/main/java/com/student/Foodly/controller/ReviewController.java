package com.student.Foodly.controller;

// Module: Member 6 - Review Moderation

import com.student.Foodly.model.Review;
import com.student.Foodly.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ReviewController.java
 * Demonstrates CRUD annotations via Action Modals dashboard.
 */
@Controller
@RequestMapping("/admin/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    private boolean checkLoggedIn(RedirectAttributes ra) {
        if (UserController.currentUser == null) {
            ra.addFlashAttribute("errorMessage", "Log in to see reviews.");
            return false;
        }
        return true;
    }
    
    // Add checkAdmin for moderation tasks
    private boolean checkAdmin(RedirectAttributes ra) {
        if (UserController.currentUser == null || !UserController.currentUser.getRole().equals("ADMIN")) {
            ra.addFlashAttribute("errorMessage", "Admin Access Denied.");
            return false;
        }
        return true;
    }

    @GetMapping
    public String showReviewsDashboard(Model model, RedirectAttributes ra) {
        if (!checkLoggedIn(ra)) return "redirect:/login";
        
        model.addAttribute("reviews", reviewService.readAll());
        model.addAttribute("newReview", new Review()); // Embedded object for th:object binding
        model.addAttribute("pageTitle", "Reviews Dashboard - Foodly");
        return "admin/reviews";
    }

    @PostMapping("/add")
    public String postReview(@ModelAttribute("newReview") Review review, RedirectAttributes ra) {
            
        if (!checkLoggedIn(ra)) return "redirect:/login";

        reviewService.create(review);

        ra.addFlashAttribute("successMessage", "Review posted!");
        return "redirect:/admin/reviews";
    }


    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/login";
        
        reviewService.delete(id);
        
        ra.addFlashAttribute("successMessage", "Review deleted!");
        return "redirect:/admin/reviews";
    }
}
