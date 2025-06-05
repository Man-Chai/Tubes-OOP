package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.dto.UserRegisterDto;
import com.example.service.UserService;

/**
 * This controller is disabled to avoid conflict with AuthController
 * which now handles registration functionality.
 */
// @Controller 
public class RegisterController {

    @Autowired
    private UserService userService;

    // Method renamed to avoid conflict with AuthController
    // @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterDto());
        return "register";
    }

    // Method renamed to avoid conflict with AuthController
    // @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") UserRegisterDto userDto, Model model) {
        try {
            userService.registerNewUser(userDto); // Using the correct method name
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
