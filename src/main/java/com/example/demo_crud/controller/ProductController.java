package com.example.demo_crud.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_crud.entity.Product;
import com.example.demo_crud.helper.HandleError;
import com.example.demo_crud.model.RequestProduct;
import com.example.demo_crud.model.ResponeProduct;
import com.example.demo_crud.model.ResponseData;
import com.example.demo_crud.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:8081")
@RestController()
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Data products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Iterable<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping()
    public ResponseEntity<ResponseData<ResponeProduct>> create(@Valid @RequestBody RequestProduct requestProduct,
            Errors errors) {
        ResponseData<ResponeProduct> responseData = new ResponseData<>();
        Product product = modelMapper.map(requestProduct, Product.class);
        if (errors.hasErrors()) {
            HandleError handleError = new HandleError(errors);

            responseData.setMessage(handleError.getError());
            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(false);
        ResponeProduct responeProduct = modelMapper.map(productService.save(product, requestProduct),
                ResponeProduct.class);
        responseData.setData(responeProduct);
        return ResponseEntity.ok(responseData);

    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") String id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ResponeProduct>> update(@PathVariable Long id,
            @Valid @RequestBody RequestProduct requestProduct, Errors errors) {
        ResponseData<ResponeProduct> responseData = new ResponseData<>();
        Product product = modelMapper.map(requestProduct, Product.class);
        product.setId(id);
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(false);
        ResponeProduct responeProduct = modelMapper.map(productService.save(product, requestProduct),
                ResponeProduct.class);
        responseData.setData(responeProduct);
        return ResponseEntity.ok(responseData);

    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable String id) {
        productService.removeOne(id);
    }
}
