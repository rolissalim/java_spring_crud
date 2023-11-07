package com.example.demo_crud.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Role;
import com.example.demo_crud.entity.User;
import com.example.demo_crud.model.RequestUser;
import com.example.demo_crud.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    public Page<User> findPagingByParams(String keyword, Pageable pageable) {
        return userRepository.findByNameContains(keyword, pageable);

    }

    public User save(User user, RequestUser requestUser) {
        modelMapper.map(requestUser, User.class);
        try {
            Role role = new Role();
            role = modelMapper.map(roleService.findById(requestUser.getRole_id()), Role.class);
            if (!role.getId().equals(null))
                user.setRoles(role);

        } catch (Exception e) {
            // user.setRoles(null);
        }
        return userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).get();
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

}
