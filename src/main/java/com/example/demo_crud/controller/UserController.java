package com.example.demo_crud.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Errors;
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

import com.example.demo_crud.entity.User;
import com.example.demo_crud.model.RequestUser;
import com.example.demo_crud.model.ResponseData;
import com.example.demo_crud.model.ResponseDataPaging;
import com.example.demo_crud.model.ResponseUser;
import com.example.demo_crud.repository.UserRepository;
import com.example.demo_crud.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:7071")
@RestController()
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Data users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<ResponseDataPaging<List<User>>> getData(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        ResponseDataPaging<List<User>> responseData = new ResponseDataPaging<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<User> data = userService.findPagingByParams(keyword, pageable);
            responseData.setStatus(true);
            responseData.setData(data.getContent());
            responseData.setCount(data.getNumberOfElements());
            responseData.setCurrentPage(page);
            responseData.setTotalPage(data.getTotalPages());
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.setData(null);
            responseData.setCount(0);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<ResponseData<ResponseUser>> create(@RequestBody RequestUser requestUser, Errors errors) {
        ResponseData<ResponseUser> responseData = new ResponseData<>();
        ResponseUser responseUser = new ResponseUser();
        try {
            // user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            requestUser.setPassword(requestUser.getPassword());
            User user = modelMapper.map(requestUser, User.class);

            if (errors.hasErrors()) {
                responseData.setStatus(false);
                responseData.setData(null);
                for (ObjectError error : errors.getAllErrors()) {
                    responseData.getMessage().add(error.getDefaultMessage());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }

            responseData.setStatus(true);
            responseUser = modelMapper.map(userService.save(user, requestUser), ResponseUser.class);

            responseData.setData(responseUser);
            responseData.getMessage().add("Data berhasil disimpan");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.getMessage().add("Save failed");
            modelMapper.map(requestUser, responseUser);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ResponseUser>> update(@PathVariable("id") String id,
            @RequestBody RequestUser requestUser,
            Errors errors) {
        ResponseData<ResponseUser> responseData = new ResponseData<>();
        ResponseUser responseUser = new ResponseUser();
        try {
            // user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            User _user = userService.findById(id);

            if (errors.hasErrors()) {
                responseData.setStatus(false);
                responseData.setData(null);
                for (ObjectError error : errors.getAllErrors()) {
                    responseData.getMessage().add(error.getDefaultMessage());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }

            responseData.setStatus(true);
            responseUser = modelMapper.map(userService.save(_user, requestUser), ResponseUser.class);

            responseData.setData(responseUser);
            responseData.getMessage().add("save success");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.getMessage().add("Save failed");
            modelMapper.map(requestUser, responseUser);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ResponseUser>> getDetail(@PathVariable("id") String id) {
        ResponseData<ResponseUser> responseData = new ResponseData<>();
        ResponseUser responseUser = new ResponseUser();
        try {
            responseData.setStatus(true);
            responseUser = modelMapper.map(userService.findById(id), ResponseUser.class);

            responseData.setData(responseUser);
            responseData.getMessage().add("save success");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.getMessage().add("Data not found");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
