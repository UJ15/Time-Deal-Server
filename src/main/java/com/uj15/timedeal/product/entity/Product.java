package com.uj15.timedeal.product.entity;

import com.uj15.timedeal.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    private UUID id;

    @Column(length = 50)
    private String name;

    @Column(length = 500)
    private String description;

    @Column
    private long price;

    @Column
    private LocalDateTime dealTime;

    @Builder
    private Product(String name, String description, long price, LocalDateTime dealTime) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.dealTime = dealTime;
    }
}
