package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "payment")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    // ID's Generate Privately
    private Long id;
        
    @NotNull(message = "ID Game harus dipilih")
    private Long idgame;

    @NotNull(message = "Metode harus dipilih")
    public String metode;
    public String username;

    // Blank constructor (Default)
    public Payment() {}

    // Parameterized constructor
    public Payment(String metode, String username) {
        this.metode = metode;
        this.username = username;
    }

    // Getter & Setter section
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdgame() {
        return idgame;
    }

    public void setIdgame(Long idgame) {
        this.idgame = idgame;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    // Debugging section (Optional)
    @Override
    public String toString() {
        return "Payment{" +
                    "id='" + id + "\'" +
                    ", username='" + username + '\'' +
                    ", id game='" + idgame + '\'' +
                    ", metode='" + metode + "\'" +
                "}";
    }
}