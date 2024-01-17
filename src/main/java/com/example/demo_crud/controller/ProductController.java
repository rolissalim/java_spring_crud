package com.example.demo_crud.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_crud.entity.Product;
import com.example.demo_crud.entity.Supplier;
import com.example.demo_crud.helper.HandleError;
import com.example.demo_crud.model.RequestProduct;
import com.example.demo_crud.model.ResponseProduct;
import com.example.demo_crud.model.ResponseData;
import com.example.demo_crud.model.ResponseDataPaging;
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
    public ResponseEntity<ResponseDataPaging<List<Product>>> getData(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String order,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {

        ResponseDataPaging<List<Product>> responseData = new ResponseDataPaging<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<Product> data = productService.findDataByParams(keyword, pageable);
            responseData.setStatus(true);
            responseData.setData(data.getContent());
            responseData.setCount(data.getNumberOfElements());
            responseData.setCurrentPage(page);
            responseData.setTotalPage(data.getTotalPages());
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.setData(null);
            responseData.setCount(0);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<ResponseData<ResponseProduct>> create(@Valid @RequestBody RequestProduct requestProduct,
            Errors errors) {
        ResponseData<ResponseProduct> responseData = new ResponseData<>();
        try {
            Product product = modelMapper.map(requestProduct, Product.class);
            if (errors.hasErrors()) {
                HandleError handleError = new HandleError(errors);

                responseData.setMessage(handleError.getError());
                responseData.setStatus(false);
                responseData.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }

            ResponseProduct responeProduct = modelMapper.map(productService.save(product, requestProduct),
                    ResponseProduct.class);
            responseData.setData(responeProduct);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {

        }
        responseData.setStatus(false);
        responseData.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ResponseProduct>> update(@PathVariable Long id,
            @Valid @RequestBody RequestProduct requestProduct, Errors errors) {
        ResponseData<ResponseProduct> responseData = new ResponseData<>();
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
        ResponseProduct responeProduct = modelMapper.map(productService.save(product, requestProduct),
                ResponseProduct.class);
        responseData.setData(responeProduct);
        return ResponseEntity.ok(responseData);

    }

    @PostMapping("/add-suppliers/{id}")
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long productId) {
        productService.addSupplier(supplier, productId);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable Long id) {
        productService.removeOne(id);
    }
}
