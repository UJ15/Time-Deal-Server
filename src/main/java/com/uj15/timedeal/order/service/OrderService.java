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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;

    private final RedissonClient redissonClient;

    private final RTopic topic;

    public OrderService(
            OrderRepository orderRepository,
            ProductService productService,
            UserService userService,
            RedissonClient redissonClient, RTopic topic) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
        this.redissonClient = redissonClient;
        this.topic = topic;
    }

    @Transactional
    public synchronized void createOrder(UUID productId, UserPrincipal userPrincipal) {
        RLock lock = redissonClient.getLock(productId.toString());

        try {
            lock.tryLock(1, 1, TimeUnit.SECONDS);

            orderRepository.findByProductIdAndUserId(productId, userPrincipal.getUserId())
                    .ifPresent(o -> {
                        throw new IllegalArgumentException("order is already exist");
                    });

            Order order = getOrder(productId, userPrincipal);

            orderRepository.save(order);


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            topic.publish("unlock");
        }
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
