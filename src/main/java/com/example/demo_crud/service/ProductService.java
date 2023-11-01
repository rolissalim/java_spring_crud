package com.example.demo_crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Product;
import com.example.demo_crud.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ProductService {
     @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).get();
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
}
