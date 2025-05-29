package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.TopUp;

public interface TopUpRepository extends JpaRepository<TopUp, Long> {
    List<TopUp> findByUsername(String username);
}
