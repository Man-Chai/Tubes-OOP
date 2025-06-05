package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.UserRegisterDto;
import com.example.model.User;
import com.example.service.UserService;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("dto", new UserRegisterDto());
        return "register";
    }
    
    @GetMapping("/register.html")
    public String showRegisterHtml(Model model) {
        model.addAttribute("dto", new UserRegisterDto());
        return "register";
    }

    @GetMapping("/test-register")
    public String testRegister() {
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegisterDto dto, Model model) {
        System.out.println("REGISTER ATTEMPT: " + dto.getUsername());
        
        try {
            // Validasi username
            if (userService.existsByUsername(dto.getUsername())) {
                model.addAttribute("error", "Username sudah digunakan");
                model.addAttribute("dto", dto);
                return "register";
            }
            
            // Validasi password
            if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
                model.addAttribute("error", "Password tidak boleh kosong");
                model.addAttribute("dto", dto);
                return "register";
            }
            
            // Validasi konfirmasi password
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                model.addAttribute("error", "Password dan konfirmasi password tidak sama");
                model.addAttribute("dto", dto);
                return "register";
            }
            
            System.out.println("REGISTERING USER: " + dto.getUsername());
            
            // Register the user using UserService
            User savedUser = userService.registerNewUser(dto);
            System.out.println("USER SAVED: " + savedUser.getId() + ", " + savedUser.getUsername() + ", ROLE: " + savedUser.getRole());
            
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            System.out.println("ERROR SAVING USER: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Terjadi kesalahan saat mendaftarkan pengguna: " + e.getMessage());
            model.addAttribute("dto", dto);
            return "register";
        }
    }
    
    @GetMapping("/create-test-users")
    public String createTestUsers(Model model) {
        try {
            // Create admin user if not exists
            if (!userService.existsByUsername("admin")) {
                UserRegisterDto adminDto = new UserRegisterDto();
                adminDto.setUsername("admin");
                adminDto.setPassword("admin123");
                adminDto.setConfirmPassword("admin123");
                adminDto.setRole("ADMIN");
                userService.registerNewUser(adminDto);
            }
            
            // Create regular user if not exists
            if (!userService.existsByUsername("user")) {
                UserRegisterDto userDto = new UserRegisterDto();
                userDto.setUsername("user");
                userDto.setPassword("user123");
                userDto.setConfirmPassword("user123");
                userDto.setRole("USER");
                userService.registerNewUser(userDto);
            }
            
            model.addAttribute("message", "Test users created successfully! Admin: admin/admin123, User: user/user123");
            return "test-result";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating test users: " + e.getMessage());
            return "test-result";
        }
    }
    
    @GetMapping("/auth-info")
    public String authInfo(Authentication auth, Model model) {
        if (auth == null) {
            model.addAttribute("message", "Tidak ada pengguna yang terautentikasi");
            return "test-result";
        } else {
            String username = auth.getName();
            User user = userService.findByUsername(username);
            
            model.addAttribute("message", 
                "User terautentikasi: " + username + "<br>" +
                "User role: " + user.getRole() + "<br>" +
                "Authorities: " + auth.getAuthorities()
            );
            return "test-result";
        }
    }
    
    @GetMapping("/verify-password")
    public String verifyPassword(@ModelAttribute("username") String username, 
                              @ModelAttribute("password") String password,
                              Model model) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            User user = userService.findByUsername(username);
            if (user != null) {
                boolean matches = userService.verifyPassword(password, user.getPassword());
                model.addAttribute("message", 
                    "Username: " + username + "<br>" +
                    "Password Matches: " + (matches ? "YES" : "NO") + "<br>" +
                    "User Role: " + user.getRole() + "<br>" +
                    "Stored Hash: " + user.getPassword()
                );
            } else {
                model.addAttribute("message", "User not found: " + username);
            }
        } else {
            model.addAttribute("message", "Please provide username and password parameters");
        }
        return "test-result";
    }
}
