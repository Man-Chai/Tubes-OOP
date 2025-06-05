package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dto.UserRegisterDto;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.CustomUserDetailsService;
import com.example.service.UserService;
import com.example.topupgame.TopupgameApplication;

@SpringBootTest(classes = TopupgameApplication.class)
public class AuthenticationFlowTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void testUserRegistration() {
        // Create test user
        UserRegisterDto dto = new UserRegisterDto();
        String testUsername = "testuser_" + System.currentTimeMillis();
        dto.setUsername(testUsername);
        dto.setPassword("testpassword");
        dto.setConfirmPassword("testpassword");
        dto.setRole("USER");
        
        // Register user
        User savedUser = userService.registerNewUser(dto);
        
        // Verify user was created correctly
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(testUsername, savedUser.getUsername());
        assertEquals("USER", savedUser.getRole());
        assertTrue(passwordEncoder.matches("testpassword", savedUser.getPassword()));
        
        // Cleanup
        userRepository.delete(savedUser);
    }
    
    @Test
    public void testUserAuthentication() {
        // Create test user
        UserRegisterDto dto = new UserRegisterDto();
        String testUsername = "testauth_" + System.currentTimeMillis();
        dto.setUsername(testUsername);
        dto.setPassword("testpassword");
        dto.setConfirmPassword("testpassword");
        dto.setRole("USER");
        
        User savedUser = userService.registerNewUser(dto);
        
        // Test authentication through UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(testUsername);
        assertNotNull(userDetails);
        assertEquals(testUsername, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        
        // Verify password matches
        assertTrue(passwordEncoder.matches("testpassword", userDetails.getPassword()));
        
        // Cleanup
        userRepository.delete(savedUser);
    }
    
    @Test
    public void testRoleBasedAccess() {
        // Create admin user
        UserRegisterDto adminDto = new UserRegisterDto();
        String adminUsername = "testadmin_" + System.currentTimeMillis();
        adminDto.setUsername(adminUsername);
        adminDto.setPassword("adminpassword");
        adminDto.setConfirmPassword("adminpassword");
        adminDto.setRole("ADMIN");
        
        User adminUser = userService.registerNewUser(adminDto);
        
        // Load and authenticate admin
        UserDetails adminDetails = userDetailsService.loadUserByUsername(adminUsername);
        Authentication adminAuth = new UsernamePasswordAuthenticationToken(
            adminDetails, null, adminDetails.getAuthorities());
        
        // Verify admin role
        assertTrue(adminAuth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        
        // Create regular user
        UserRegisterDto userDto = new UserRegisterDto();
        String regularUsername = "testregular_" + System.currentTimeMillis();
        userDto.setUsername(regularUsername);
        userDto.setPassword("userpassword");
        userDto.setConfirmPassword("userpassword");
        userDto.setRole("USER");
        
        User regularUser = userService.registerNewUser(userDto);
        
        // Load and authenticate regular user
        UserDetails userDetails = userDetailsService.loadUserByUsername(regularUsername);
        Authentication userAuth = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        
        // Verify user role
        assertTrue(userAuth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        
        // Cleanup
        userRepository.delete(adminUser);
        userRepository.delete(regularUser);
    }
}
