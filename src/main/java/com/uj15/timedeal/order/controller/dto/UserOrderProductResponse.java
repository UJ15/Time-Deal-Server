package com.uj15.timedeal.order.controller.dto;

import com.uj15.timedeal.order.entity.Order;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOrderProductResponse {

    private final UUID orderId;
    private final UUID productId;
    private final String productName;

    public static UserOrderProductResponse from(Order order) {
        return new UserOrderProductResponse(
                order.getId(),
                order.getProductId(),
                order.getProductName()
        );
    }
}
