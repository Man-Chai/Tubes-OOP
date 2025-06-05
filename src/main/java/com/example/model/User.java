package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT")
    private Long id;    private String username;
    private String katasandi; // password field in database
    private String role; // <--- PASTIKAN ADA INI (not in original DB but needed for security)
    private String fullname; // Required by database schema
    private String email; // Required by database schema
    private String phone; // Optional field from database

    public User() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }    public String getPassword() {
        return katasandi;
    }

    public void setPassword(String password) {
        this.katasandi = password;
    }

    public String getRole() {
        return role;
    }    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPresent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
