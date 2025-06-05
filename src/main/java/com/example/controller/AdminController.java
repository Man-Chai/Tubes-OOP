package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.UserService;
import com.example.repository.TopUpRepository;

/**
 * Controller for admin dashboard and management functions.
 * Only accessible by users with ADMIN role.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
      @Autowired
    private UserService userService;
    
    @Autowired
    private TopUpRepository topUpRepository;
      @GetMapping
    public String adminDashboard(Model model, Authentication authentication) {
        // Get current admin user info
        String username = authentication.getName();
        
        // Add admin info and topups to model
        model.addAttribute("adminUsername", username);
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("topups", topUpRepository.findAll());
        
        return "admin";
    }
      @GetMapping("/users")
    public String manageUsers(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("adminUsername", username);
        model.addAttribute("users", userService.getAllUsers());
        
        return "admin-users";
    }
    
    @GetMapping("/hapus/{id}")
    public String hapusTopup(@PathVariable Long id, Authentication authentication) {
        // Only admins can delete topups
        topUpRepository.deleteById(id);
        return "redirect:/admin";
    }
}
