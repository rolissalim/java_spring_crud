package com.example.demo_crud.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Product> findDataByParams(String keyword, String order, Integer start, Integer limit) {
        return productRepository.findDataByParams(keyword);
    }

    public Product save(Product product, RequestProduct requestProduct) {
        try {
            Category category = new Category();
            category = modelMapper.map(categoryService.findById(requestProduct.getCategory_id()), Category.class);
            if (!category.getId().equals(null))
                product.setCategory(category);

        } catch (Exception e) {
            product.setCategory(null);
        }

        return productRepository.save(product);
    }

    public Product saveWithSupplier(Product product) {
        return productRepository.save(product);
    }

    public Product findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            return null;
        return productRepository.findById(id).get();
    }

    public void removeOne(String id) {
        productRepository.deleteById(id);
    }

    public void addSupplier(Supplier supplier, String productId) {
        Product product = findById(productId);
        if (product == null)
            throw new RuntimeException("Data not found");
        product.getSuppliers().add(supplier);
        saveWithSupplier(product);

    }
}
