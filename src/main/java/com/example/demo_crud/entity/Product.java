package com.example.demo_crud.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Size(message = "Max is 30 character", max = 30)
    private String name;

    private String description;

    private double price;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name = "tbl_product_supplier", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private Set<Supplier> suppliers;

    @OneToMany
    // @JoinTable(name = "fk_product_images", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    // @JoinTable(name = "fk_images_product", joinColumns = @JoinColumn(name = "id"))
    @JoinColumn(name = "fk_images_product",referencedColumnName = "id")
    private List<Images> images;

    public Product(Long id,
            String name,
            String description,
            double price,
            Category category,
            Set<Supplier> suppliers,
            List<Images> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.suppliers = suppliers;
        this.images = images;
    }

    public Product() {

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

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        if (!images.isEmpty())
            this.images = images;
        else
            this.images = null;

    }

}
