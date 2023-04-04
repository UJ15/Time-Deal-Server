package com.uj15.timedeal.order.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.order.controller.dto.ProductOrderUserResponse;
import com.uj15.timedeal.order.controller.dto.UserOrderProductResponse;
import com.uj15.timedeal.order.entity.Order;
import com.uj15.timedeal.order.repository.OrderRepository;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.product.service.ProductService;
import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductService productService;

    @Mock
    UserService userService;

    @InjectMocks
    OrderService orderService;

    @Nested
    @Disabled
    @DisplayName("createOrder 메서드 테스트")
    class DescribeCreateOrder {

        Product product;
        Order order;
        User user;

        UserPrincipal principal;

        @BeforeEach
        void objectSetUp() {

            product = Product.builder()
                    .name("test")
                    .description("description")
                    .price(1000)
                    .dealTime(LocalDateTime.now())
                    .quantity(100)
                    .build();

            user = User.of("test", "password", Role.USER);

            order = Order.builder()
                    .product(product)
                    .user(user)
                    .build();

            principal = UserPrincipal.from(user);
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID를 받을 경우 IllegalArgumentException을 반환한다.")
        void itThrowIllegalArgumentExceptionWhenNotExistProductId() {
            //given
            when(orderRepository.findByProductIdAndUserId(any(), any())).thenReturn(Optional.empty());
            when(productService.getProduct(any())).thenThrow(IllegalArgumentException.class);

            //then
            Assertions.assertThatThrownBy(() -> orderService.createOrder(UUID.randomUUID(), principal))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("존재하지 않는 유저 ID를 받을 경우 IllegalArgumentException을 반환한다.")
        void itThrowIllegalArgumentExceptionWhenNotExistUserId() {
            //given
            when(orderRepository.findByProductIdAndUserId(any(), any())).thenReturn(Optional.empty());
            when(productService.getProduct(any())).thenReturn(product);
            when(userService.getUser(any())).thenThrow(IllegalArgumentException.class);

            //then
            Assertions.assertThatThrownBy(() -> orderService.createOrder(UUID.randomUUID(), principal))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("이미 존재하는 주문일 경우 IllegalArgumentException을 반환한다.")
        void itThrowIllegalArgumentExceptionWhenExistOrder() {
            //given

            when(orderRepository.findByProductIdAndUserId(any(), any())).thenReturn(Optional.of(order));

            //then
            Assertions.assertThatThrownBy(() -> orderService.createOrder(UUID.randomUUID(), principal))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("존재하지 않는 주문일 경우 orderRepository Save 메서드를 호출한다.")
        void itCallRepositorySave() {
            //given
            when(orderRepository.findByProductIdAndUserId(any(), any())).thenReturn(Optional.empty());
            when(productService.getProduct(any())).thenReturn(product);
            when(userService.getUser(any())).thenReturn(user);

            //when
            orderService.createOrder(UUID.randomUUID(), principal);

            //then
            verify(orderRepository).save(any());
        }

        @Disabled
        @RepeatedTest(100)
        @DisplayName("동시성 테스트.")
        void concurrencyProblem() throws InterruptedException {
            //when
            int threadCount = 100;

            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);
            User user = User.of("test", "testest", Role.USER);
            UserPrincipal principal = UserPrincipal.from(user);

            when(orderRepository.findByProductIdAndUserId(any(), any())).thenReturn(Optional.empty());
            when(productService.getProduct(any())).thenReturn(product);
            when(userService.getUser(any())).thenReturn(user);

            //when
            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                            try {
                                orderService.createOrder(product.getId(), principal);
                            } finally {
                                latch.countDown();
                            }
                        }
                );
            }

            latch.await();

            Product actual = productService.getProduct(product.getId());

            //then
            Assertions.assertThat(actual.getQuantity()).isZero();
        }
    }

    @Nested
    @DisplayName("getUserOrderProducts 메서드 테스트")
    class DescribeGetUserOrderProducts {

        Product product;
        Order order;
        User user;

        @BeforeEach
        void objectSetUp() {

            product = Product.builder()
                    .name("test")
                    .description("description")
                    .price(1000)
                    .dealTime(LocalDateTime.now())
                    .quantity(5)
                    .build();

            user = User.of("test", "password", Role.USER);

            order = Order.builder()
                    .product(product)
                    .user(user)
                    .build();
        }

        @Test
        @DisplayName("유저 id를 받을 경우 orderRepository findById 메서드를 호출한다.")
        void itCallRepositorySave() {
            //given
            when(orderRepository.findByUserId(any())).thenReturn(List.of(order));

            //when
            List<UserOrderProductResponse> responseDto = orderService.getUserOrderProducts(user.getId());

            //then
            verify(orderRepository).findByUserId(any());
            Assertions.assertThat(responseDto.get(0).getOrderId())
                    .isEqualTo(UserOrderProductResponse.from(order).getOrderId());
        }
    }

    @Nested
    @DisplayName("getProductOrderUsers 메서드 테스트")
    class DescribeProductOrderUsers {

        Product product;
        Order order;
        User user;

        @BeforeEach
        void objectSetUp() {

            product = Product.builder()
                    .name("test")
                    .description("description")
                    .price(1000)
                    .dealTime(LocalDateTime.now())
                    .quantity(5)
                    .build();

            user = User.of("test", "password", Role.USER);

            order = Order.builder()
                    .product(product)
                    .user(user)
                    .build();
        }

        @Test
        @DisplayName("상품 id를 받을 경우 orderRepository findByProduct 메서드를 호출한다.")
        void itCallRepositoryFindByProductId() {
            //given
            when(orderRepository.findByProductId(any())).thenReturn(List.of(order));

            //when
            List<ProductOrderUserResponse> responseDto = orderService.getProductOrderUsers(product.getId());

            //then
            verify(orderRepository).findByProductId(any());
            Assertions.assertThat(responseDto.get(0).getOrderId())
                    .isEqualTo(UserOrderProductResponse.from(order).getOrderId());
        }
    }
}