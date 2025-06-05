package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + username);
        
        User user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println("USER NOT FOUND: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        System.out.println("USER FOUND: " + user.getUsername() + ", ROLE: " + user.getRole());
        System.out.println("PASS HASH: " + user.getPassword());
        
        // Create authorities
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        
        // Create and return UserDetails
        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
            
        System.out.println("UserDetails created successfully for: " + username);
        System.out.println("Authorities: " + userDetails.getAuthorities());
        
        return userDetails;
    }
}
