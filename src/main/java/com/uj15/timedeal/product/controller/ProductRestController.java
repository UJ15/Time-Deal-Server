package com.uj15.timedeal.product.controller;

import com.uj15.timedeal.common.auth.annotation.Authorization;
import com.uj15.timedeal.product.controller.dto.ProductCreateRequest;
import com.uj15.timedeal.product.controller.dto.ProductSelectResponse;
import com.uj15.timedeal.product.controller.dto.ProductUpdateRequest;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.product.service.ProductService;
import com.uj15.timedeal.user.Role;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Authorization(role = Role.ADMIN)
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductSelectResponse getProduct(@PathVariable UUID id) {
        Product product = productService.getProduct(id);

        return ProductSelectResponse.from(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductSelectResponse> getProducts() {
        return productService.getProducts().stream()
                .map(ProductSelectResponse::from)
                .collect(Collectors.toList());
    }
}
