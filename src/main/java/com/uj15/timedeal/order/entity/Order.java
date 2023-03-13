package com.uj15.timedeal.order.entity;

import com.uj15.timedeal.common.entity.BaseEntity;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Order(User user, Product product) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.product = product;
    }

    public UUID getProductId() {
        return product.getId();
    }

    public String getProductName() {
        return product.getName();
    }
}
