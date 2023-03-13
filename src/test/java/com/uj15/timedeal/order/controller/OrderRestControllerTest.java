package com.uj15.timedeal.order.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uj15.timedeal.auth.SessionConst;
import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.order.controller.dto.ProductOrderUserResponse;
import com.uj15.timedeal.order.controller.dto.UserOrderProductResponse;
import com.uj15.timedeal.order.entity.Order;
import com.uj15.timedeal.order.service.OrderService;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.util.ControllerSetUp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = OrderRestController.class)
class OrderRestControllerTest extends ControllerSetUp {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    private static final String BASE_URL = "/api/orders";

    @Nested
    @DisplayName("createOrder 메서드 테스트(상품 생성)")
    class DescribeCreateOrder {

        MockHttpSession session = new MockHttpSession();

        @Test
        @DisplayName("UserRole이 User일 경우 Created 반환, service createOrder 메서드 호출")
        void itCreateOrderReturnOk() throws Exception {
            //given
            User user = User.of("consumer", "password", Role.USER);
            session.setAttribute(SessionConst.KEY.name(), UserPrincipal.from(user));

            Product product = Product.builder()
                    .name("test")
                    .description("description")
                    .price(1000)
                    .dealTime(LocalDateTime.now())
                    .quantity(5)
                    .build();

            UUID id = product.getId();

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL + "/" + product.getId())
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            //then
            response.andExpect(status().isCreated());
            verify(orderService).createOrder(any(), any());
        }
    }

    @Nested
    @DisplayName("getUserOrderProducts 메서드 테스트(유저 주문 상품 목록)")
    class DescribeGetUserOrderProducts {

        MockHttpSession session = new MockHttpSession();

        @Test
        @DisplayName("UserRole이 User일 경우 ok 반환, service getUserOrderProducts 메서드 호출")
        void itReturnUserOrderProducts() throws Exception {
            //given
            User user = User.of("consumer", "password", Role.USER);
            session.setAttribute(SessionConst.KEY.name(), UserPrincipal.from(user));

            Product product = Product.builder()
                    .name("test")
                    .description("description")
                    .price(1000)
                    .dealTime(LocalDateTime.now())
                    .quantity(5)
                    .build();

            Order order = Order.builder()
                    .product(product)
                    .user(user)
                    .build();

            List<UserOrderProductResponse> responseDto = List.of(UserOrderProductResponse.from(order));

            when(orderService.getUserOrderProducts(any())).thenReturn(responseDto);

            //when
            ResultActions response = mockMvc.perform(
                    get(BASE_URL)
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            //then
            response.andExpect(status().isOk());
            verify(orderService).getUserOrderProducts(any());
        }
    }

    @Nested
    @DisplayName("getProductOrderUsers 메서드 테스트(상품 주문 유저 목록)")
    class DescribeGetProductOrderUsers {

        MockHttpSession session = new MockHttpSession();

        @Test
        @DisplayName("UserRole이 User일 경우 ok 반환, service getUserOrderProducts 메서드 호출")
        void itReturnUserOrderProducts() throws Exception {
            //given
            User user = User.of("consumer", "password", Role.USER);
            session.setAttribute(SessionConst.KEY.name(), UserPrincipal.from(user));

            Product product = Product.builder()
                    .name("test")
                    .description("description")
                    .price(1000)
                    .dealTime(LocalDateTime.now())
                    .quantity(5)
                    .build();

            UUID id = product.getId();

            Order order = Order.builder()
                    .product(product)
                    .user(user)
                    .build();

            List<ProductOrderUserResponse> responseDto = List.of(ProductOrderUserResponse.from(order));

            when(orderService.getProductOrderUsers(any())).thenReturn(responseDto);

            //when
            ResultActions response = mockMvc.perform(
                    get(BASE_URL + "/" + id)
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            //then
            response.andExpect(status().isOk());
            verify(orderService).getProductOrderUsers(any());
        }
    }
}