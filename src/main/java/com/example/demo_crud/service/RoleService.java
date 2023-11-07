package com.example.demo_crud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Role;
import com.example.demo_crud.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Page<Role> findPagingByParams(String keyword, Pageable pageable) {
        return roleRepository.findByNameContains(keyword, pageable);
    }

    public Iterable<Role> findDataByPaging(String keyword, Integer start, Integer limit, Pageable pageable) {
        return roleRepository.findByNameContains(keyword, pageable);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role findById(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent())
            return null;
        return roleRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

}
