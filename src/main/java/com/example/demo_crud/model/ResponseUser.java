package com.example.demo_crud.model;

import java.sql.Timestamp;

import com.example.demo_crud.entity.Role;

import jakarta.persistence.ManyToOne;

public class ResponseUser {
    private String id;

    private String name;

    private String email;

    @ManyToOne
    private Role role;
    private Long role_id;

    private Timestamp created_at;

    private Timestamp updated_at;

    public ResponseUser() {
    }

    public ResponseUser(String id, String name, String email, Role role, Long role_id, Timestamp created_at,
            Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.role_id = role_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}
