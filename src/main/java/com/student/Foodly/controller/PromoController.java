package com.student.Foodly.controller;

// Module: Member 5 - Promos Management

import com.student.Foodly.model.Promo;
import com.student.Foodly.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * PromoController.java
 * Handles the HTTP routes and maps to Action Modals for Promos.
 */
@Controller
@RequestMapping("/admin/promos")
public class PromoController {

    @Autowired
    private PromoService promoService;

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
    public String showPromosDashboard(Model model, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        
        List<Promo> promos = promoService.getAllPromos();
        model.addAttribute("promos", promos);
        model.addAttribute("newPromo", new Promo()); // Embedded object for th:object binding
        model.addAttribute("pageTitle", "Promotions & Discounts - Member 5");
        return "admin/promos";
    }

    /**
     * CREATE / ADD
     */
    @PostMapping("/add")
    public String addPromo(@ModelAttribute("newPromo") Promo promo, RedirectAttributes ra) {
            
        if (!checkAdmin(ra)) return "redirect:/";

        promoService.createPromo(promo);

        ra.addFlashAttribute("successMessage", "Promotion Added Successfully!");
        return "redirect:/admin/promos";
    }

    /**
     * UPDATE / EDIT
     */
    @PostMapping("/edit")
    public String editPromo(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String promoCode,
            @RequestParam double discountPercentage,
            @RequestParam(required = false, defaultValue = "false") boolean isActive,
            RedirectAttributes ra) {
            
        if (!checkAdmin(ra)) return "redirect:/";

        Promo updatedPromo = new Promo(id, name, promoCode, discountPercentage, isActive);
        promoService.updatePromo(updatedPromo);

        ra.addFlashAttribute("successMessage", "Promotion Updated Successfully!");
        return "redirect:/admin/promos";
    }

    /**
     * DELETE
     */
    @GetMapping("/delete/{id}")
    public String deletePromo(@PathVariable String id, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        
        promoService.deletePromo(id);
        
        ra.addFlashAttribute("successMessage", "Promotion Removed!");
        return "redirect:/admin/promos";
    }
}
