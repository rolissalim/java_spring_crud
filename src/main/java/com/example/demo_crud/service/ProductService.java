package com.example.demo_crud.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Category;
import com.example.demo_crud.entity.Product;
import com.example.demo_crud.entity.Supplier;
import com.example.demo_crud.model.RequestProduct;
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
    private CategoryService categoryService;

    public Page<Product> findDataByParams(String keyword, Pageable pageable) {
        return productRepository.findDataByParams(keyword, pageable);
    }

    public Product save(Product product, RequestProduct requestProduct) {
        modelMapper.map(requestProduct, product);
        try {
            Category category = new Category();
            category = modelMapper.map(categoryService.findById(requestProduct.getCategory_id()), Category.class);
            if (!category.getId().equals(null))
                product.setCategory(category);

        } catch (Exception e) {

        }

        return productRepository.save(product);
    }

    public Product saveWithSupplier(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            return null;
        return productRepository.findById(id).get();
    }

    public void removeOne(Long id) {
        productRepository.deleteById(id);
    }

    public void addSupplier(Supplier supplier, Long productId) {
        Product product = findById(productId);
        if (product == null)
            throw new RuntimeException("Data not found");
        product.getSuppliers().add(supplier);
        saveWithSupplier(product);

    }
}
