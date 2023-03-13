package com.uj15.timedeal.order.controller.dto;

import com.uj15.timedeal.order.entity.Order;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOrderUserResponse {

    private final UUID orderId;
    private final UUID userId;
    private final String userName;

    public static ProductOrderUserResponse from(Order order) {
        return new ProductOrderUserResponse(
                order.getId(),
                order.getUserId(),
                order.getUsername()
        );
    }
}
