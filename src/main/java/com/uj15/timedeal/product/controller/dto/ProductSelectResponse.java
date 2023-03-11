package com.uj15.timedeal.product.controller.dto;

import com.uj15.timedeal.product.entity.Product;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSelectResponse {

    private final UUID id;
    private final String name;
    private final String description;
    private final long quantity;
    private final long price;
    private final LocalDateTime dealTime;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static ProductSelectResponse from(Product product) {
        return new ProductSelectResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getDealTime(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
