package com.ecommerce.project.service.category;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.ListResourceResponse;

public interface CategoryService {
    ListResourceResponse<CategoryDTO> getAllCategories(Integer pageNumber, Integer pageSize, String sortby,
            String sortOrder);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO updateCategory(long categoryId, CategoryDTO category) throws RuntimeException;

    CategoryDTO deleteCategory(long categoryId) throws RuntimeException;
}
