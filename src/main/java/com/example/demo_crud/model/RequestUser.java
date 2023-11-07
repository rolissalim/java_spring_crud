package com.example.demo_crud.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RequestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, length = 36)
    private String id;

    @NotEmpty(message = "Name is Required")
    private String name;

    @Column(unique = true)
    @NotEmpty(message = "Email is Required")
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Password is Required")
    private String password;

    @NotEmpty(message = "Password is Required")
    private Integer role_id;

    public RequestUser() {
    }

    public RequestUser(String name, String email, String password, Integer role_id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

}
