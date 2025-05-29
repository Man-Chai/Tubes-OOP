package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserRegisterDto;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(UserRegisterDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return false;
        }

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER"); // Default role
        userRepository.save(user);
        return true;
    }
}
