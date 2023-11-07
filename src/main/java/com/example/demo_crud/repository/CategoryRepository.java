package com.example.demo_crud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_crud.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // @Query("SELECT p FROM Category p WHERE p.name LIKE :keyword%")
    Page<Category> findByNameContains(String name, Pageable pageable);

}
