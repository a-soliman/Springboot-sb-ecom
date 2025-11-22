package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories();

    Category createCategory(Category category);

    Category updateCategory(long categoryId, Category category) throws RuntimeException;

    String deleteCategory(long categoryId) throws RuntimeException;
}
