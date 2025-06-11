package com.example.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        System.out.println("Auth provider configured with: " + userDetailsService.getClass().getName());
        return provider;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF tetap aktif (default)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/", "/register", "/register.html", "/register/**", "/test-register",
                    "/create-test-users", "/login", "/css/**", "/js/**", "/images/**", 
                    "/auth-info", "/verify-password", "/debug/**", "/itemlist"
                ).permitAll()
                .requestMatchers("/admin/**", "/admin").hasRole("ADMIN")
                .requestMatchers("/itemlist").hasRole("ADMIN")
                .requestMatchers("/tambahItem").hasRole("ADMIN")
                .requestMatchers("/user/**", "/user").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/tambah").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    System.out.println("Authentication success for user: " + authentication.getName());
                    System.out.println("User authorities: " + authentication.getAuthorities());
                    
                    try {
                        if (authentication.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                            response.sendRedirect("/admin");
                        } else {
                            response.sendRedirect("/user");
                        }
                    } catch (IOException e) {
                        System.out.println("Error during redirect: " + e.getMessage());
                        e.printStackTrace();
                    }
                })
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
