package com.example.demo_crud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Supplier;
import com.example.demo_crud.repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public Page<Supplier> findPagingByParams(String keyword, Pageable pageable) {
        return supplierRepository.findDataByParams(keyword, pageable);
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier findById(Long id) {
        Optional<Supplier> product = supplierRepository.findById(id);
        if (!product.isPresent())
            return null;
        return supplierRepository.findById(id).get();
    }

    public void removeOne(Long id) {
        supplierRepository.deleteById(id);
    }
}
