package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.ItemList;
import com.example.repository.ItemListRepository;

@Controller
public class ItemListController {

    @Autowired
    private ItemListRepository repo;

    // Tampilkan semua item di halaman
    @GetMapping("/itemlist")
    public String showAllItems(Model model) {
        System.out.println("DEBUG: Memanggil repo.findAll()");
        try {
            model.addAttribute("items", repo.findAll());
            return "itemlist";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Gagal load itemlist: " + e.getMessage());
            return "error";
        }
    }

    // Tampilkan form untuk menambah item baru
    @GetMapping("/tambahItem")    
    public String formItem(Model model) {
        ItemList itemBaru = new ItemList();
        model.addAttribute("itemBaru", itemBaru);
        return "tambahItem";
    }

    // Simpan item baru dari form
    @PostMapping("/tambahItem")
    public String tambahItem(@ModelAttribute("itemBaru") ItemList item, Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            repo.save(item); // Ini menyimpan item baru
            return "redirect:/itemlist";
        } else {
            return "redirect:/user";
        }
    }

    @GetMapping("/editItem/{id}")
    public String editItem(@PathVariable Long id, Model model, Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            ItemList item = repo.findById(id).orElse(null);
            if (item != null) {
                model.addAttribute("editItem", item);
                return "editItem";
            } else {
                model.addAttribute("error", "Item tidak ditemukan");
                return "error";
            }
        } else {
            return "redirect:/user";
        }
    }

    // Simpan item baru dari form
    @PostMapping("/editItem")
    public String updateItem(@ModelAttribute("editItem") ItemList item, Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            repo.save(item); // Ini menyimpan item yang sudah diedit
            return "redirect:/itemlist";
        } else {
            return "redirect:/user";
        }
    }

    // Hapus item berdasarkan ID
    @GetMapping("/hapusItem/{id}")
    public String deleteItem(@PathVariable Long id, Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            repo.deleteById(id);
            return "redirect:/itemlist";
        } else {
            return "redirect:/user";
        }
    }
}