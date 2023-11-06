package com.example.demo_crud.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.User;
import com.example.demo_crud.model.ResponseUser;
import com.example.demo_crud.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Long countData(String keyword) {
        return userRepository.countFindDataByParams(keyword);
    }

    public List<ResponseUser> findDataByParams(String keyword, String order, Integer start, Integer limit) {
        List<User> users = new ArrayList<User>();
        List<ResponseUser> responseUsers = new ArrayList<ResponseUser>();
        try {
            users = userRepository.findDataByParams(keyword);

            modelMapper.map(users, responseUsers);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return responseUsers;

    }

    public User save(User user) {
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
