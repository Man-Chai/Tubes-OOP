package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.TopUp;
import com.example.repository.ItemListRepository;
import com.example.repository.TopUpRepository;

@Controller
public class TopUpController {
    
    @Autowired
    private TopUpRepository repo;    // Admin page handling moved to AdminController

    @GetMapping("/user")
    public String userPage(Model model, Authentication authentication) {
        // Menggunakan authentication objek yang diinjeksi
        if (authentication == null) {
            // Tidak terotentikasi, kembalikan ke halaman login
            return "redirect:/login";
        }
        
        String username = authentication.getName();
        List<TopUp> userTopups = repo.findByUsername(username);
        
        // Langsung menggunakan model, bukan URL parameter
        model.addAttribute("topups", userTopups);
        model.addAttribute("username", username);
        return "user";
    }

    @Autowired
    private ItemListRepository itemRepo;

    @GetMapping("/tambah")
    public String formTopup(Model model, Authentication authentication) {
        TopUp topUp = new TopUp();
        if (authentication != null) {
            topUp.setUsername(authentication.getName());
        }

        model.addAttribute("topUp", topUp);
        model.addAttribute("items", itemRepo.findAll()); // kirim item ke view
        return "tambah";
    }

    @PostMapping("/tambah")
    public String tambahTopup(TopUp topUp, Authentication authentication) {
        // Pastikan username diisi dengan user yang login
        if (authentication != null && (topUp.getUsername() == null || topUp.getUsername().trim().isEmpty())) {
            topUp.setUsername(authentication.getName());
        }
        
        repo.save(topUp);
        return "redirect:/user";
    }    @GetMapping("/hapus/{id}")
    public String hapusTopup(@PathVariable Long id, Authentication authentication) {
        // Only allow deletion by the owner or admin
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            repo.deleteById(id);
            return "redirect:/admin";
        } else {
            // Regular users cannot delete topups
            return "redirect:/user";
        }
    }
}
