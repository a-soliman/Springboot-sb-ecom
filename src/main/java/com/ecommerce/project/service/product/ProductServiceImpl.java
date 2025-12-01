package com.ecommerce.project.service.product;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ListResourceResponse;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
        private String RESOURCE = "product";
        private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private ModelMapper modelMapper;

        @Override
        public ListResourceResponse<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize, String sortby,
                        String sortOrder) {
                logger.info("getAllProducts was called with pageNumber {}, pageSize {}, sortby {}, sortOrder {}",
                                pageNumber, pageSize, sortby, sortOrder);
                Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                                ? Sort.by(sortby).ascending()
                                : Sort.by(sortby).descending();
                Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
                Page<Product> productPage = productRepository.findAll(pageDetails);
                final List<Product> products = productPage.getContent();
                final List<ProductDTO> productDTOs = products.stream()
                                .map(product -> modelMapper.map(product, ProductDTO.class))
                                .toList();
                final ListResourceResponse<ProductDTO> response = ListResourceResponse.<ProductDTO>builder()
                                .content(productDTOs)
                                .pageNumber(productPage.getNumber())
                                .pageSize(productPage.getSize())
                                .totalElements(productPage.getTotalElements())
                                .totalPages(productPage.getTotalPages())
                                .lastPage(productPage.isLast())
                                .build();
                logger.info("getAllProducts returning {} products", productDTOs.size());
                return response;
        }

        @Override
        public ProductDTO createProduct(Long categoryId, Product product) {
                logger.info("createProduct was called with categoryId {}", categoryId);

                final Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

                product.setCategory(category);
                product.calculateAndSetSpecialPrice();
                product.setImage("default.png");

                final Product savedProduct = productRepository.save(product);
                final ProductDTO productDTO = modelMapper.map(savedProduct, ProductDTO.class);
                logger.info("Product created with id {}, on category {}", productDTO.getProductId(), categoryId);
                return productDTO;

        }

}
