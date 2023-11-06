package com.example.demo_crud.service;

import java.util.List;
import java.util.Optional;

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

    public Long countData(String keyword) {
        return supplierRepository.countFindDataByParams(keyword);
    }

    public List<Supplier> findDataByParams(String keyword, String order, Integer start,
            Integer limit) {
        return supplierRepository.findDataByParams(keyword);
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier findById(String id) {
        Optional<Supplier> product = supplierRepository.findById(id);
        if (!product.isPresent())
            return null;
        return supplierRepository.findById(id).get();
    }

    public void removeOne(String id) {
        supplierRepository.deleteById(id);
    }
}
