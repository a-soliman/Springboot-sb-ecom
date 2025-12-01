package com.ecommerce.project.service.product;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ListResourceResponse;
import com.ecommerce.project.payload.ProductDTO;

public interface ProductService {
    ListResourceResponse<ProductDTO> getAllProducts(
            Integer pageNumber, Integer pageSize, String sortby, String sortOrder);

    ProductDTO createProduct(Long categoryId, Product product);
}
