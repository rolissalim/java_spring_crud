package com.example.demo_crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Role;
import com.example.demo_crud.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Long countData(String keyword) {
        return roleRepository.countByNameContains(keyword);
    }

    public List<Role> findDataByParams(String keyword, Integer start, Integer limit) {
        return roleRepository.findByNameContains(keyword);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}
