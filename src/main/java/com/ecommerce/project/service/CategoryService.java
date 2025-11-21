package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.model.Category;

public interface CategoryService {
    List<Category> getAllCategories();

    Category createCategory(Category category);

    Category updateCategory(long categoryId, Category category) throws RuntimeException;

    String deleteCategory(long categoryId) throws RuntimeException;
}
