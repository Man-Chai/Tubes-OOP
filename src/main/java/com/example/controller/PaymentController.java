package com.example.controller;

import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import com.example.repository.TopUpRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private TopUpRepository topupRepo;

    // // Menampilkan semua metode pembayaran
    // @GetMapping("/payment")
    // public String listPayment(Model model) {
    //     model.addAttribute("payments", repo.findAll());
    //     return "payment";
    // }

    // Tampilkan form untuk menambah metode baru
    @GetMapping("/tambahPayment")
    public String formTambahPayment(Model model, Authentication authentication) {
        Payment payment = new Payment();
        String username = authentication != null ? authentication.getName() : null;

        if (username != null) {
            payment.setUsername(username);
            model.addAttribute("topups", topupRepo.findByUsername(username));
        } else {
            model.addAttribute("topups", new ArrayList<>());
        }

        model.addAttribute("paymentBaru", payment);
        return "tambahPayment";
    }

    // Simpan metode pembayaran baru
    @PostMapping("/tambahPayment")
    public String simpanPayment(@ModelAttribute("paymentBaru") Payment payment, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            System.out.println("Terjadi error binding: " + result);
            return "tambahPayment";
        }

        if (authentication != null && (payment.getUsername() == null || payment.getUsername().trim().isEmpty())) {
            payment.setUsername(authentication.getName());
        }

        repo.save(payment);
        return "redirect:/user";
    }

    // // Hapus metode pembayaran
    // @GetMapping("/hapusPayment/{id}")
    // public String hapusPayment(@PathVariable Long id, Authentication authentication) {
    //     if (authentication != null && authentication.getAuthorities().stream()
    //             .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
    //         repo.deleteById(id);
    //         return "redirect:/user";
    //     } else {
    //         return "redirect:/user";
    //     }
    // }
}