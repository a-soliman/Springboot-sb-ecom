package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.ResourceListEmptyException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.exceptions.ResourceUniquenessViolationException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    private String RESOURCE = "category";
    private String RESOURCE_LIST = "categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        final List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ResourceListEmptyException(RESOURCE_LIST);
        }
        final List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        final CategoryResponse response = new CategoryResponse(categoryDTOs);
        return response;
    }

    @Override
    public CategoryDTO createCategory(Category category) {
        final Category dbCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (dbCategory != null) {
            throw new ResourceUniquenessViolationException(RESOURCE, "categoryName", category.getCategoryName());
        }
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO response = modelMapper.map(savedCategory, CategoryDTO.class);
        return response;
    }

    @Override
    public Category updateCategory(long categoryId, Category category) throws RuntimeException {
        // Throw if we have another category with the same name
        Category dbCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (dbCategory != null) {
            throw new ResourceUniquenessViolationException(RESOURCE, "categoryName", category.getCategoryName());
        }

        // Throw if we can't find the category in the db
        dbCategory = categoryRepository.findById(categoryId).orElse(null);
        if (dbCategory == null) {
            throw new ResourceNotFoundException(RESOURCE, "categoryId", categoryId);
        }

        // Update the name on the retrieved category
        dbCategory.setCategoryName(category.getCategoryName());

        // Save the updated category in the db and return it
        final Category updatedCategory = categoryRepository.save(dbCategory);
        return updatedCategory;
    }

    @Override
    public String deleteCategory(long categoryId) throws RuntimeException {
        try {
            final Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category == null) {
                throw new ResourceNotFoundException(RESOURCE, "categoryId", categoryId);
            }

            categoryRepository.deleteById(categoryId);

        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("id was not found");
        }

        return "Category with categoryId: " + categoryId + " deleted successfully";
    }
}
