package com.uj15.timedeal.order.service;

import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.order.controller.dto.ProductOrderUserResponse;
import com.uj15.timedeal.order.controller.dto.UserOrderProductResponse;
import com.uj15.timedeal.order.entity.Order;
import com.uj15.timedeal.order.repository.OrderRepository;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.product.service.ProductService;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.service.UserService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;

    public OrderService(
            OrderRepository orderRepository,
            ProductService productService,
            UserService userService
    ) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional
    public synchronized void createOrder(UUID productId, UserPrincipal userPrincipal) {
        orderRepository.findByProductIdAndUserId(productId, userPrincipal.getUserId())
                .ifPresent(o -> {
                    throw new IllegalArgumentException("order is already exist");
                });

        Order order = getOrder(productId, userPrincipal);

        orderRepository.save(order);
    }

    @Transactional
    public List<UserOrderProductResponse> getUserOrderProducts(UUID userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(UserOrderProductResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductOrderUserResponse> getProductOrderUsers(UUID productId) {
        return orderRepository.findByProductId(productId).stream()
                .map(ProductOrderUserResponse::from)
                .collect(Collectors.toList());
    }

    private Order getOrder(UUID productId, UserPrincipal userPrincipal) {
        Product product = productService.getProduct(productId);
        User user = userService.getUser(userPrincipal);

        productService.decreaseProductStock(product);

        return Order.builder()
                .product(product)
                .user(user)
                .build();
    }
}
