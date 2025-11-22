package com.ecommerce.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.payload.ApiResponse.ApiResponse;
import com.ecommerce.project.payload.ApiResponse.SuccessfulApiResponse;
import com.ecommerce.project.service.CategoryService;

import jakarta.validation.Valid;

import static com.ecommerce.project.constants.ApiEndpoints.PUBLIC_CATEGORIES_URL;
import static com.ecommerce.project.constants.ApiEndpoints.ADMIN_CATEGORIES_URL;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CategoryController {
    private CategoryService categoryService;
    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(PUBLIC_CATEGORIES_URL)
    public ResponseEntity<ApiResponse<CategoryResponse>> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder) {
        logger.info("getAllCategories was invoked");

        final CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy,
                sortOrder);
        final SuccessfulApiResponse<CategoryResponse> response = new SuccessfulApiResponse<CategoryResponse>(
                categoryResponse);
        return new ResponseEntity<ApiResponse<CategoryResponse>>(response, HttpStatus.OK);
    }

    @PostMapping(PUBLIC_CATEGORIES_URL)
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        final CategoryDTO createdCategoryDto = categoryService.createCategory(categoryDTO);
        final SuccessfulApiResponse<CategoryDTO> response = new SuccessfulApiResponse<CategoryDTO>(createdCategoryDto);
        return new ResponseEntity<ApiResponse<CategoryDTO>>(response, HttpStatus.CREATED);
    }

    @PutMapping(ADMIN_CATEGORIES_URL + "/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        final CategoryDTO updatedCategoryDto = categoryService
                .updateCategory(categoryId, categoryDTO);
        final SuccessfulApiResponse<CategoryDTO> response = new SuccessfulApiResponse<CategoryDTO>(updatedCategoryDto);
        return new ResponseEntity<ApiResponse<CategoryDTO>>(response, HttpStatus.OK);
    }

    @DeleteMapping(ADMIN_CATEGORIES_URL + "/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> deleteCategory(@PathVariable long categoryId) {
        final CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
        final SuccessfulApiResponse<CategoryDTO> response = new SuccessfulApiResponse<CategoryDTO>(deletedCategoryDTO);
        return new ResponseEntity<ApiResponse<CategoryDTO>>(response, HttpStatus.OK);
    }

}
