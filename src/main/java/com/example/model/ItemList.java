package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "itemlist")
public class ItemList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; // Unique identifier for the item

    // Attributes
    public String namaItem;
    public String tipeItem;
    public int hargaItem;

    // Default constructor
    public ItemList() {}

    // Parameterized constructor
    public ItemList(String namaItem, String tipeItem, int hargaItem) {
        this.namaItem = namaItem;
        this.tipeItem = tipeItem;
        this.hargaItem = hargaItem;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getnamaItem() {
        return namaItem;
    }

    public void setnamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public String gettipeItem() {
        return tipeItem;
    }

    public void settipeItem(String tipeItem) {
        this.tipeItem = tipeItem;
    }

    public int gethargaItem() {
        return hargaItem;
    }

    public void sethargaItem(int hargaItem) {
        this.hargaItem = hargaItem;
    }

    // Override toString for better debugging and logging
     @Override
    public String toString() {
        return "ItemList{" +
                "id=" + id +
                ", namaItem='" + namaItem + '\'' +
                ", tipeItem='" + tipeItem + '\'' +
                ", hargaItem=" + hargaItem +
                '}';
    }
}

