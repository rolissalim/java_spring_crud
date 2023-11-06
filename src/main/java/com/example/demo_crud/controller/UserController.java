package com.example.demo_crud.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo_crud.model.ResponseData;
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
    public ResponseEntity<List<ResponseUser>> getData(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String order,
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer limit) {

        try {
            List<ResponseUser> responseUsers = new ArrayList<ResponseUser>();
            responseUsers = userService.findDataByParams(keyword, order, start, limit);

            if (responseUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(responseUsers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<ResponseData<ResponseUser>> create(@RequestBody User user, Errors errors) {
        ResponseData<ResponseUser> responseData = new ResponseData<>();
        ResponseUser responseUser = new ResponseUser();
        try {
            // user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            User _user = userService
                    .save(new User(
                            user.getName(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getCreatedAt(),
                            user.getUpdatedAt()));

            if (errors.hasErrors()) {
                responseData.setStatus(false);
                responseData.setData(null);
                for (ObjectError error : errors.getAllErrors()) {
                    responseData.getMessage().add(error.getDefaultMessage());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }

            responseData.setStatus(true);
            responseUser = modelMapper.map(userService.save(_user), ResponseUser.class);

            responseData.setData(responseUser);
            responseData.getMessage().add("Data berhasil disimpan");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.getMessage().add("Save failed");
            modelMapper.map(user, responseUser);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<ResponseUser>> update(@PathVariable("id") String id, @RequestBody User user,
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
            responseUser = modelMapper.map(userService.save(_user), ResponseUser.class);

            responseData.setData(responseUser);
            responseData.getMessage().add("save success");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.getMessage().add("Save failed");
            modelMapper.map(user, responseUser);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ResponseUser>> getDetail(@PathVariable("id") String id, @RequestBody User user,
            Errors errors) {
        ResponseData<ResponseUser> responseData = new ResponseData<>();
        ResponseUser responseUser = new ResponseUser();
        try {
            // user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            User _user = userService.findById(id);

            responseData.setStatus(true);
            responseUser = modelMapper.map(userService.save(_user), ResponseUser.class);

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
