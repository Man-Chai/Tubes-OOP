package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserRegisterDto;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerNewUser(UserRegisterDto dto) {
        // Validate username is not taken
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // Validate passwords match
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }        // Create new user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER"); // Semua registrasi otomatis mendapat role USER
        user.setFullname(dto.getFullName() != null ? dto.getFullName() : dto.getUsername());
        user.setEmail(dto.getEmail() != null ? dto.getEmail() : dto.getUsername() + "@example.com");
        user.setPhone(dto.getPhone()); // Can be null
        
        return userRepository.save(user);
    }
    
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    /**
     * Creates default users for testing purposes
     */
    public void createDefaultUsers() {        // Create default admin user if not exists
        if (!existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setFullname("Administrator");
            admin.setEmail("admin@example.com");
            admin.setPhone("08123456789");
            userRepository.save(admin);
            System.out.println("Created default admin user: admin/admin123");
        }
        
        // Create default regular user if not exists
        if (!existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            user.setFullname("Test User");
            user.setEmail("user@example.com");
            user.setPhone("08198765432");
            userRepository.save(user);
            System.out.println("Created default user: user/user123");
        }
        
        // Create another test user
        if (!existsByUsername("testuser")) {
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setPassword(passwordEncoder.encode("test123"));
            testUser.setRole("USER");
            testUser.setFullname("Test User 2");
            testUser.setEmail("testuser@example.com");
            testUser.setPhone("08111223344");
            userRepository.save(testUser);
            System.out.println("Created test user: testuser/test123");
        }
    }
    
    /**
     * Get all users for debugging purposes
     */
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
