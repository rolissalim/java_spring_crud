package com.example.demo_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_crud.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // @Query("SELECT r Role r WHERE r.name LIKE :keyword%")
    List<Role> findByNameContains(String name);

    // @Query("SELECT p FROM Role p WHERE p.name LIKE :name%")
    Long countByNameContains(String name);

}
