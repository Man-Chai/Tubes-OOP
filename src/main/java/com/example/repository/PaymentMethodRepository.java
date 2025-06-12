package com.example.repository;

import com.example.model.PaymentMethod;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    // List<PaymentMethod> findByNama(String nama);
}
