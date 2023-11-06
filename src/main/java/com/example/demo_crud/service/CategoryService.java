package com.example.demo_crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_crud.entity.Category;
import com.example.demo_crud.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Long countData(String keyword) {
        return categoryRepository.countFindDataByParams(keyword);
    }

    public List<Category> findDataByParams(String keyword, String order, Integer start, Integer limit) {
        return categoryRepository.findDataByParams(keyword);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent())
            return null;
        return categoryRepository.findById(id).get();
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void removeOne(Long id) {
        categoryRepository.deleteById(id);
    }

}
