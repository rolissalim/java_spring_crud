package com.example.demo_crud.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false,length = 36)
    private String id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne
    private Role roles;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = false)
    private Timestamp updated_at;

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public User() {

    }

    public User(String name, String email, String password, Timestamp created_at,
            Timestamp updated_at) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getID() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void createdAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getCreatedAt() {
        return this.created_at;
    }

    public void updatedAt(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getUpdatedAt() {
        return this.updated_at;
    }

}
