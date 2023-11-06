package com.example.demo_crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo_crud.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT p FROM Category p WHERE p.name LIKE :keyword%")
    List<Category> findDataByParams(String keyword);

    // @Query("SELECT p FROM Category p WHERE p.name LIKE :name%")
    Long countByNameContains(String name);

    @Query("SELECT COUNT(*) FROM Category p WHERE p.name LIKE :keyword%")
    Long countFindDataByParams(String keyword);


}
