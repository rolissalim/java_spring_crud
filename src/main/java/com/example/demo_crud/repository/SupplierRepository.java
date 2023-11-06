package com.example.demo_crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo_crud.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    @Query("SELECT s FROM Supplier s WHERE s.name LIKE :keyword%")
    public List<Supplier> findDataByParams(String keyword);

    @Query("SELECT s FROM Supplier s WHERE s.name LIKE :name%")
    Long countFindDataByParams(String keyword);

}
