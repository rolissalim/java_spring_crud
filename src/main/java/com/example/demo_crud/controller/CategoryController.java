package com.example.demo_crud.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_crud.entity.Category;
import com.example.demo_crud.model.CategoryData;
import com.example.demo_crud.model.ResponseData;
import com.example.demo_crud.model.ResponseDataPaging;
import com.example.demo_crud.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:8081")
@RestController()
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Data categories")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<ResponseDataPaging<List<Category>>> getData(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String order,
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer limit) {
        ResponseDataPaging<List<Category>> responseData = new ResponseDataPaging<>();
        Long count = 0L;
        try {
            List<Category> data = new ArrayList<>();
            data = categoryService.findDataByParams(keyword, order, start, limit);
            count = categoryService.countData(keyword);
            if (data.isEmpty()) {
                responseData.setCount(count);
                responseData.setStatus(true);
                responseData.setData(null);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
            responseData.setStatus(true);
            responseData.setData(data);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.setData(null);
            responseData.setCount(count);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryData categoryData, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            responseData.setStatus(false);
            responseData.setData(null);
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Category category = modelMapper.map(categoryData, Category.class);

        responseData.setStatus(true);
        responseData.setData(categoryService.save(category));
        responseData.getMessage().add("Data berhasil disimpan");

        return ResponseEntity.ok(responseData);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Category>> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryData categoryData, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();
        Category category = modelMapper.map(categoryData, Category.class);
        category.setId(id);
        if (errors.hasErrors()) {
            responseData.setStatus(false);
            responseData.setData(category);
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setData(categoryService.save(category));
        responseData.getMessage().add("Data berhasil disimpan");

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable Long id) {
        categoryService.removeOne(id);
    }
}
