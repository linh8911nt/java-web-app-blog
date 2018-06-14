package com.codegym.service;

import com.codegym.model.Category;

public interface CategoryService {

    Iterable<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    void delete(Long id);
}
