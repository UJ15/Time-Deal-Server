package com.uj15.timedeal.order.service;

import com.uj15.timedeal.order.entity.Order;
import com.uj15.timedeal.order.repository.OrderRepository;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.product.repository.ProductRepository;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.repository.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createOrder(UUID productId, UUID userId) {
        orderRepository.findByProductIdAndUserId(productId, userId)
                .ifPresent(o -> {
                    throw new IllegalArgumentException("order is already exist");
                });

        Order order = getOrder(productId, userId);

        orderRepository.save(order);
    }

    private Order getOrder(UUID productId, UUID userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not exist product"));

        product.decrease();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not exist user"));

        return Order.builder()
                .product(product)
                .user(user)
                .build();
    }
}
