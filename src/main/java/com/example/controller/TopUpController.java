package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.model.TopUp;
import com.example.repository.TopUpRepository;

import java.util.List;

@Controller
public class TopUpController {

    @Autowired
    private TopUpRepository repo;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("topups", repo.findAll());
        return "admin";
    }

    @GetMapping("/user")
    public String userPage(@RequestParam String username, Model model) {
        List<TopUp> userTopups = repo.findByUsername(username);
        model.addAttribute("topups", userTopups);
        return "user";
    }

    @GetMapping("/tambah")
    public String formTopup(TopUp topUp) {
        return "tambah";
    }

    @PostMapping("/tambah")
    public String tambahTopup(TopUp topUp) {
        repo.save(topUp);
        return "redirect:/user?username=" + topUp.getUsername();
    }

    @GetMapping("/hapus/{id}")
    public String hapusTopup(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin";
    }
}
