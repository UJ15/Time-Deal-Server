package com.uj15.timedeal.product.service;

import com.uj15.timedeal.product.controller.dto.ProductCreateRequest;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void createProduct(ProductCreateRequest request) {
        Assert.notNull(request, "ProductCreateRequest is null");

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .dealTime(request.getDealTime())
                .build();

        productRepository.save(product);
    }
}
