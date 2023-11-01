package com.example.demo_crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Supplier;
import com.example.demo_crud.repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Iterable<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier findById(String id) {
        return supplierRepository.findById(id).get();
    }

    public void deleteById(String id) {
        supplierRepository.deleteById(id);
    }
}
