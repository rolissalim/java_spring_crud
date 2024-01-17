package com.example.demo_crud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo_crud.entity.Product;

public interface ProductRepository
        extends PagingAndSortingRepository<Product, Long>, CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE :keyword%")
    Page<Product> findDataByParams(String keyword, Pageable pageable);

}
