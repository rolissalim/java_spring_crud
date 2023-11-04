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

import com.example.demo_crud.entity.Supplier;
import com.example.demo_crud.model.ResponseData;
import com.example.demo_crud.model.SupplierData;
import com.example.demo_crud.service.SupplierService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:8081")
@RestController()
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier", description = "Data suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Iterable<Supplier> findAll() {
        return supplierService.findAll();
    }

    @PostMapping()
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierData supplierData, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();
        Supplier supplier = modelMapper.map(supplierData, Supplier.class);
        if (errors.hasErrors()) {
            responseData.setStatus(false);
            responseData.setData(supplier);
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setData(supplierService.save(supplier));
        responseData.getMessage().add("Data berhasil disimpan");

        return ResponseEntity.ok(responseData);

    }

    @GetMapping("/{id}")
    public Supplier findById(@PathVariable("id") String id) {
        return supplierService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Supplier>> update(@PathVariable Long id,
            @Valid @RequestBody SupplierData supplierData, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();
        Supplier supplier = modelMapper.map(supplierData, Supplier.class);
        supplier.setId(id);
        if (errors.hasErrors()) {
            responseData.setStatus(false);
            responseData.setData(supplier);
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setData(supplierService.save(supplier));
        responseData.getMessage().add("Data berhasil disimpan");

        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable String id) {
        supplierService.removeOne(id);
    }
}
