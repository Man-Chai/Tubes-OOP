package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "topup")
public class TopUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String username;
    public String game;
    public int nominal;
    public int harga;

    // Constructor kosong (diperlukan oleh JPA)
    public TopUp() {}

    // Constructor lengkap
    public TopUp(String username, String game, int nominal, int harga) {
        this.username = username;
        this.game = game;
        this.nominal = nominal;
        this.harga = harga;
    }

    // Getter dan Setter

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
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    // toString() opsional
    @Override
    public String toString() {
        return "TopUp{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", game='" + game + '\'' +
                ", nominal=" + nominal +
                ", harga=" + harga +
                '}';
    }
}
