package com.example.demo_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_crud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
