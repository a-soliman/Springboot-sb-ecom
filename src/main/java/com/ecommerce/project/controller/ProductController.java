package com.ecommerce.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ListResourceResponse;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ApiResponse.ApiResponse;
import com.ecommerce.project.payload.ApiResponse.SuccessfulApiResponse;
import com.ecommerce.project.service.product.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.ecommerce.project.constants.ApiEndpoints.PUBLIC_PRODUCTS_URL;
import static com.ecommerce.project.constants.ApiEndpoints.ADMIN_CREATE_PRODUCT_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(PUBLIC_PRODUCTS_URL)
    public ResponseEntity<ApiResponse<ListResourceResponse<ProductDTO>>> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder) {
        logger.info("getAllProducts was invoked");

        final ListResourceResponse<ProductDTO> productResponse = productService.getAllProducts(
                pageNumber, pageSize, sortBy, sortOrder);

        final SuccessfulApiResponse<ListResourceResponse<ProductDTO>> response = new SuccessfulApiResponse<ListResourceResponse<ProductDTO>>(
                productResponse);
        return new ResponseEntity<ApiResponse<ListResourceResponse<ProductDTO>>>(response, HttpStatus.OK);
    }

    @PostMapping(ADMIN_CREATE_PRODUCT_URL)
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(
            @Valid @RequestBody Product product,
            @PathVariable Long categoryId) {
        logger.info("createProduct was invoked");

        final ProductDTO createdProductDto = productService.createProduct(categoryId, product);
        final SuccessfulApiResponse<ProductDTO> response = new SuccessfulApiResponse<ProductDTO>(createdProductDto);
        return new ResponseEntity<ApiResponse<ProductDTO>>(response, HttpStatus.CREATED);
    }

}
