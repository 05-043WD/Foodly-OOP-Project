package com.student.Foodly.controller;

import com.student.Foodly.model.Staff;
import com.student.Foodly.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * StaffController.java
 *
 * Module: Member 4 - Staff Management
 * Handles staff creation, updates, deletion, and dashboard view.
 */
@Controller
@RequestMapping("/admin/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    private boolean checkAdmin(RedirectAttributes ra) {
        if (UserController.currentUser == null || !UserController.currentUser.getRole().equals("ADMIN")) {
            ra.addFlashAttribute("errorMessage", "Access Denied.");
            return false;
        }
        return true;
    }

    @GetMapping
    public String dashboard(Model model, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        List<Staff> staffList = staffService.getAllStaff();
        model.addAttribute("staffList", staffList);
        model.addAttribute("newStaff", new Staff()); // Embedded object for th:object binding
        model.addAttribute("pageTitle", "Staff Management - Member 4");
        return "admin/staff";
    }

    @PostMapping("/add")
    public String addStaff(@ModelAttribute("newStaff") Staff staff, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        
        staffService.createStaff(staff);
        
        ra.addFlashAttribute("successMessage", "New staff member added.");
        return "redirect:/admin/staff";
    }

    @PostMapping("/edit")
    public String editStaff(
            @RequestParam String id, @RequestParam String name,
            @RequestParam String email, @RequestParam String role,
            @RequestParam String shift, @RequestParam double salary,
            RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        
        staffService.updateStaff(new Staff(id, name, email, role, shift, salary));
        ra.addFlashAttribute("successMessage", "Staff updated.");
        return "redirect:/admin/staff";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable String id, RedirectAttributes ra) {
        if (!checkAdmin(ra)) return "redirect:/";
        staffService.deleteStaff(id);
        ra.addFlashAttribute("successMessage", "Staff deleted.");
        return "redirect:/admin/staff";
    }
}
