package com.uj15.timedeal.order.controller;

import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.common.auth.annotation.Authentication;
import com.uj15.timedeal.common.auth.annotation.Authorization;
import com.uj15.timedeal.order.controller.dto.UserOrderProductResponse;
import com.uj15.timedeal.order.service.OrderService;
import com.uj15.timedeal.user.Role;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Authorization(role = Role.USER)
    @PostMapping(value = "{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@PathVariable UUID id, @Authentication UserPrincipal userPrincipal) {
        orderService.createOrder(id, userPrincipal.getUserId());
    }

    @Authorization(role = Role.USER)
    @ResponseStatus(HttpStatus.OK)
    public List<UserOrderProductResponse> getUserOrderProducts(@Authentication UserPrincipal userPrincipal) {
        return orderService.getUserOrderProducts(userPrincipal.getUserId());
    }
}
