package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.UserRegisterDto;
import com.example.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegisterDto userDto, Model model) {
        boolean success = userService.register(userDto);
        if (!success) {
            model.addAttribute("error", "Password tidak cocok atau username sudah ada.");
            return "register";
        }
        return "redirect:/login?success";
    }
}
