package com.example.demo_crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo_crud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE :keyword%")
    public List<Product> findDataByParams(String keyword);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :keyword%")
    Long countFindDataByParams(String keyword);
}
