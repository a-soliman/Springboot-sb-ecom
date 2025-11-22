package com.ecommerce.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;

import jakarta.validation.Valid;

import static com.ecommerce.project.constants.ApiEndpoints.PUBLIC_CATEGORIES_URL;
import static com.ecommerce.project.constants.ApiEndpoints.ADMIN_CATEGORIES_URL;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CategoryController {
    private CategoryService categoryService;
    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(PUBLIC_CATEGORIES_URL)
    public ResponseEntity<CategoryResponse> getAllCategories() {
        logger.info("getAllCategories was invoked");
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping(PUBLIC_CATEGORIES_URL)
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category) {
        final CategoryDTO response = categoryService.createCategory(category);
        return new ResponseEntity<CategoryDTO>(response, HttpStatus.CREATED);
    }

    @PutMapping(ADMIN_CATEGORIES_URL + "/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable long categoryId,
            @RequestBody Category categoryRequestBody) {
        final Category category = Category.builder()
                .categoryName(categoryRequestBody.getCategoryName())
                .categoryId(categoryId).build();
        final CategoryDTO response = categoryService
                .updateCategory(categoryId, category);
        return new ResponseEntity<CategoryDTO>(response, HttpStatus.OK);
    }

    @DeleteMapping(ADMIN_CATEGORIES_URL + "/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
        final String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
