package com.example.demo_crud.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
@CrossOrigin(origins = "http://localhost:8081")
@RestController()
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Data users")
public class ProductController {
    
}
