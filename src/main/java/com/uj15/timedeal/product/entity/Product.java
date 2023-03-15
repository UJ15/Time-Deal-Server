package com.uj15.timedeal.product.entity;

import com.uj15.timedeal.common.entity.BaseEntity;
import com.uj15.timedeal.product.controller.dto.ProductUpdateRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50)
    private String name;

    @Column(length = 500)
    private String description;

    @Column
    private long quantity;

    @Column
    private long price;

    @Column
    private LocalDateTime dealTime;

    @Builder
    private Product(String name, String description, long quantity, long price, LocalDateTime dealTime) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.dealTime = dealTime;
    }

    public void update(ProductUpdateRequest details) {
        this.name = details.getName();
        this.description = details.getDescription();
        this.quantity = details.getQuantity();
        this.price = details.getPrice();
        this.dealTime = details.getDealTime();
        this.updatedAt = LocalDateTime.now();
    }

    public void decrease() {
        if (this.quantity == 0) {
            throw new IllegalStateException("Out of stock.");
        }

        this.quantity--;
    }
}
