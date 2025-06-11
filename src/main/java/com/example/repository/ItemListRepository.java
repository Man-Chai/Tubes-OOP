package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.ItemList;

public interface ItemListRepository extends JpaRepository<ItemList, Long> {
    List<ItemList> findByNamaItem(String namaItem);
}
