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

import com.example.demo_crud.entity.Supplier;
import com.example.demo_crud.model.ResponseData;
import com.example.demo_crud.model.ResponseDataPaging;
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
    public ResponseEntity<ResponseDataPaging<List<Supplier>>> getData(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        ResponseDataPaging<List<Supplier>> responseData = new ResponseDataPaging<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<Supplier> data = supplierService.findPagingByParams(keyword, pageable);

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
    public ResponseEntity<ResponseData<Supplier>> findById(@PathVariable("id") String id) {
        ResponseData<Supplier> responseData = new ResponseData<>();
        try {
            Supplier supllier = supplierService.findById(id);
            responseData.setStatus(true);
            responseData.setData(supllier);
            responseData.getMessage().add("Save success");
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.getMessage().add("Data not found");
            return ResponseEntity.ok(responseData);
        }

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
        responseData.getMessage().add("Save success");

        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable String id) {
        supplierService.removeOne(id);
    }
}
