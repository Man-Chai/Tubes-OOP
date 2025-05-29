package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.UserRegisterDto;
import com.example.model.User;
import com.example.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

   @GetMapping("/register")
public String showRegisterPage(Model model) {
    model.addAttribute("dto", new UserRegisterDto());
    return "register";
}


    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegisterDto dto, Model model) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            model.addAttribute("error", "Username sudah digunakan");
            return "register";
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() == null ? "USER" : dto.getRole());

        userRepo.save(user);
        return "redirect:/login?registered=true";
    }
}
