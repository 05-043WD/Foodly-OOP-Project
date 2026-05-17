package com.student.Foodly.controller;

// Module: Member 1 - User Management

import com.student.Foodly.model.User;
import com.student.Foodly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * UserController.java
 * Handles user registration, login, profile, and account deletion.
 * And Admin Add/Edit/Delete actions for users.
 */
@Controller
public class UserController {

    // Simple Global State for the Viva presentation
    public static User currentUser = null;

    @Autowired
    private UserService userService;

    /**
     * GET /register → Show registration form.
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Register – Foodly");
        return "register";
    }

    /**
     * POST /register → Save new user, log them in.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes ra) {
        if (userService.findByEmail(user.getEmail()) != null) {
            ra.addFlashAttribute("errorMessage", "Email already registered. Please use a different email.");
            return "redirect:/register";
        }
        user.setRole("USER");
        userService.create(user);
        
        // Auto-login after registration
        currentUser = user;
        
        ra.addFlashAttribute("successMessage", "Welcome to Foodly, " + user.getName() + "!");
        return "redirect:/profile/" + user.getId();
    }

    /**
     * GET /login → Show login form.
     */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("pageTitle", "Login – Foodly");
        return "login";
    }

    /**
     * POST /login → Process login using Scanner in UserService.
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes ra) {
        User tempUser = userService.loginCheck(email, password);
        
        if (tempUser != null) {
            currentUser = tempUser; // Login successful
            return "redirect:/"; // Go to homepage
        } else {
            ra.addFlashAttribute("errorMessage", "Invalid email or password.");
            return "redirect:/login";
        }
    }

    /**
     * GET /logout → Clear the session and redirect.
     */
    @GetMapping("/logout")
    public String logout(RedirectAttributes ra) {
        currentUser = null;
        ra.addFlashAttribute("successMessage", "You have successfully logged out.");
        return "redirect:/";
    }

    /**
     * GET /delete-account → Read-Modify-Overwrite user deletion.
     */
    @GetMapping("/delete-account")
    public String deleteAccount(RedirectAttributes ra) {
        if (currentUser != null) {
            // Deletes the user using the loops written in UserService
            userService.delete(currentUser.getId());
            currentUser = null; // Log them out
            ra.addFlashAttribute("successMessage", "Your account has been deleted permanently.");
        }
        return "redirect:/";
    }

    /**
     * GET /profile → Redirect to the current user's profile.
     */
    @GetMapping("/profile")
    public String myProfile(RedirectAttributes ra) {
        if (currentUser != null) {
            return "redirect:/profile/" + currentUser.getId();
        }
        ra.addFlashAttribute("errorMessage", "Please log in to view your profile.");
        return "redirect:/login";
    }

    /**
     * GET /profile/{id} → Show user profile.
     */
    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/register";
        }
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", user.getName() + " – Profile");
        return "profile";
    }

    // ===================================
    // MEMBER 1 DASHBOARD (ACTION MODALS)
    // ===================================

    @GetMapping("/admin/users")
    public String showUsersDashboard(Model model, RedirectAttributes ra) {
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            ra.addFlashAttribute("errorMessage", "Access Denied.");
            return "redirect:/";
        }
        model.addAttribute("users", userService.readAll());
        model.addAttribute("newUser", new User()); // Embedded object for th:object binding
        model.addAttribute("pageTitle", "User Management - Member 1");
        return "admin/users";
    }

    @PostMapping("/admin/users/add")
    public String adminAddUser(@ModelAttribute("newUser") User user, RedirectAttributes ra) {
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) return "redirect:/";
        
        if (userService.findByEmail(user.getEmail()) != null) {
            ra.addFlashAttribute("errorMessage", "Email already exists.");
            return "redirect:/admin/users";
        }
        
        // Similar to public registration, but does not auto-login
        userService.create(user);
        ra.addFlashAttribute("successMessage", "User registered successfully!");
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/edit")
    public String adminEditUser(@ModelAttribute User user, RedirectAttributes ra) {
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) return "redirect:/";
        
        // Preserve password if blank
        User existing = userService.findById(user.getId());
        if (existing != null && (user.getPassword() == null || user.getPassword().isBlank())) {
            user.setPassword(existing.getPassword());
        }
        
        userService.update(user);
        ra.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String adminDeleteUser(@PathVariable String id, RedirectAttributes ra) {
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) return "redirect:/";
        userService.delete(id);
        ra.addFlashAttribute("successMessage", "Account deleted successfully!");
        return "redirect:/admin/users";
    }
}
