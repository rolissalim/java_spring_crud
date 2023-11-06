package com.example.demo_crud.model;


import com.example.demo_crud.entity.Category;

import jakarta.persistence.ManyToOne;

public class ResponseProduct {

    private Long id;

    private String name;

    private String description;

    private double price;

    @ManyToOne
    private Category category;

    private Long category_id;

    public ResponseProduct() {
    }

    public ResponseProduct(Long id, String name, String description, double price, Category category, Long category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.category_id = category_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

}
