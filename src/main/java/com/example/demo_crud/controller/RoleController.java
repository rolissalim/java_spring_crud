package com.example.demo_crud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_crud.entity.Role;
import com.example.demo_crud.model.ResponseDataPaging;
import com.example.demo_crud.repository.RoleRepository;
import com.example.demo_crud.service.RoleService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:8081")
@RestController()
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Data roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @GetMapping()
    public ResponseEntity<ResponseDataPaging<List<Role>>> getData(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer limit) {
        ResponseDataPaging<List<Role>> responseData = new ResponseDataPaging<>();
        Long count = 0L;
        try {
            List<Role> data = new ArrayList<>();
            data = roleService.findDataByParams(keyword, start, limit);
            count = roleService.countData(keyword);
            if (data.isEmpty()) {
                responseData.setCount(count);
                responseData.setStatus(true);
                responseData.setData(null);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
            responseData.setStatus(true);
            responseData.setData(data);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.setData(null);
            responseData.setCount(count);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Role> create(@RequestBody Role role) {
        try {
            Role _role = roleService
                    .save(new Role(role.getName()));
            return new ResponseEntity<>(_role, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(role, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> update(@PathVariable("id") Long id, @RequestBody Role role) {
        Role data = roleService.findById(id);
        if (data != null) {
            return new ResponseEntity<>(roleRepository.save(data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") Long id) {
        try {
            roleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
