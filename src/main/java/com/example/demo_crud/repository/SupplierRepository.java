package com.example.demo_crud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo_crud.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    @Query("SELECT s FROM Supplier s WHERE s.name LIKE :keyword%")
    Page<Supplier> findDataByParams(String keyword, Pageable pageable);
}
