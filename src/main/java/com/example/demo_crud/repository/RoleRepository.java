package com.example.demo_crud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_crud.entity.Role;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>, CrudRepository<Role, Integer> {
    // @Query("SELECT r Role r WHERE r.name LIKE :keyword%")
    List<Role> findByNameContains(String name);

    // @Query("SELECT p FROM Role p WHERE p.name LIKE :name%")
    Integer countByNameContains(String name);

    Page<Role> findByNameContains(String name, Pageable pageable);

}
