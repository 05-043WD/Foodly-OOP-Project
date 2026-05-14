package com.student.Foodly.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Automatically injects the global login state into every Thymeleaf template model.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        boolean isLoggedIn = (UserController.currentUser != null);
        boolean isAdmin = isLoggedIn && "ADMIN".equals(UserController.currentUser.getRole());
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("currentUser", UserController.currentUser);
        model.addAttribute("isAdmin", isAdmin);
    }
}
