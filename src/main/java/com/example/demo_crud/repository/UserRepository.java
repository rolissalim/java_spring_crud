package com.example.demo_crud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_crud.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // @Query("SELECT u FROM User u WHERE u.name LIKE :keyword%")
    Page<User> findByNameContains(String name, Pageable pageable);

}
