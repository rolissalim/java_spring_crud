package com.example.demo_crud.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Category;
import com.example.demo_crud.entity.Product;
import com.example.demo_crud.model.RequestProduct;
import com.example.demo_crud.repository.CategoryRepository;
import com.example.demo_crud.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product save(Product product, RequestProduct requestProduct) {
        try {
            Category category = new Category();
            category = modelMapper.map(categoryRepository.findById(requestProduct.getCategory_id()), Category.class);
            product.setCategory(category);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return productRepository.save(product);
    }

    public Product findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            return null;
        return productRepository.findById(id).get();
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public void removeOne(String id) {
        productRepository.deleteById(id);
    }
}
