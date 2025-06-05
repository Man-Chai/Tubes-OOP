package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.UserService;

/**
 * Controller for testing authentication and user functionality.
 * This controller provides endpoints to verify the application's security state.
 */
@Controller
@RequestMapping("/debug")
public class DebugController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/auth-status")
    public String checkAuthStatus(Authentication authentication, Model model) {
        if (authentication == null) {
            model.addAttribute("status", "Not authenticated");
            model.addAttribute("user", null);
            model.addAttribute("roles", null);
        } else {
            model.addAttribute("status", "Authenticated");
            model.addAttribute("user", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities());
        }
        
        return "test-result";
    }
    
    @GetMapping("/create-default-users")
    public String createDefaultUsers(Model model) {
        try {
            userService.createDefaultUsers();
            model.addAttribute("message", "Default users created/verified successfully");
        } catch (Exception e) {
            model.addAttribute("error", "Error creating default users: " + e.getMessage());
        }
        
        return "test-result";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        try {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "User list retrieved successfully");
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving users: " + e.getMessage());
        }
        
        return "test-result";
    }
}
