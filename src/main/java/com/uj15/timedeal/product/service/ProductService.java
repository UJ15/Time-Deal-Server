package com.uj15.timedeal.product.service;

import com.uj15.timedeal.product.controller.dto.ProductCreateRequest;
import com.uj15.timedeal.product.controller.dto.ProductUpdateRequest;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.product.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
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

    @Transactional
    public void updateProduct(UUID id, ProductUpdateRequest request) {
        Assert.notNull(request, "ProductUpdateRequest is null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not exist product"));

        product.update(request);
    }

    public void deleteProduct(UUID id) {
        productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not exist product"));

        productRepository.deleteById(id);
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not exist product"));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
