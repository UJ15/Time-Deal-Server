package com.uj15.timedeal.product.controller;

import com.uj15.timedeal.common.auth.annotation.Authorization;
import com.uj15.timedeal.product.controller.dto.ProductCreateRequest;
import com.uj15.timedeal.product.controller.dto.ProductUpdateRequest;
import com.uj15.timedeal.product.service.ProductService;
import com.uj15.timedeal.user.Role;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @Authorization(role = Role.ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody @Valid ProductCreateRequest request) {
        productService.createProduct(request);
    }

    @Authorization(role = Role.ADMIN)
    @PatchMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductUpdateRequest request) {
        productService.updateProduct(id, request);
    }
}