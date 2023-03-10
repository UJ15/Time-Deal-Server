package com.uj15.timedeal.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uj15.timedeal.auth.SessionConst;
import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.product.controller.dto.ProductCreateRequest;
import com.uj15.timedeal.product.controller.dto.ProductUpdateRequest;
import com.uj15.timedeal.product.service.ProductService;
import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.util.ControllerSetUp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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

@WebMvcTest(controllers = ProductRestController.class)
class ProductRestControllerTest extends ControllerSetUp {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    private static final String BASE_URL = "/api/products";

    @Nested
    @DisplayName("createProduct 메서드 테스트")
    class DescribeCreateProduct {

        MockHttpSession session = new MockHttpSession();

        @Test
        @DisplayName("UserRole이 Admin일 경우 Ok 반환, service 메서드 호출")
        void itCreateProductReturnOk() throws Exception {
            //given
            User user = User.of("admin", "password", Role.ADMIN);
            session.setAttribute(SessionConst.KEY.name(), UserPrincipal.from(user));

            ProductCreateRequest requestDto = new ProductCreateRequest(
                    "product",
                    "des",
                    5,
                    1000,
                    LocalDateTime.of(
                            LocalDate.of(2023, Month.MARCH, 30),
                            LocalTime.MIDNIGHT
                    )
            );

            String requestBody = objectMapper.writeValueAsString(requestDto);

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL)
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response.andExpect(status().isCreated());
            verify(productService).createProduct(any());
        }

        @Test
        @DisplayName("UserRole이 Admin이 아닐경우 경우 UnAuthorization 반환")
        void itReturnUnAuthorization() throws Exception {
            //given
            User user = User.of("admin", "password", Role.USER);
            session.setAttribute(SessionConst.KEY.name(), UserPrincipal.from(user));

            ProductCreateRequest requestDto = new ProductCreateRequest(
                    "product",
                    "des",
                    5,
                    1000,
                    LocalDateTime.of(
                            LocalDate.of(2023, Month.MARCH, 30),
                            LocalTime.MIDNIGHT
                    )
            );

            String requestBody = objectMapper.writeValueAsString(requestDto);

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL)
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response.andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드 테스트")
    class DescribeUpdateProduct {

        MockHttpSession session = new MockHttpSession();

        @Test
        @DisplayName("UserRole이 Admin일 경우 Ok 반환, service 메서드 호출")
        void itCreateProductReturnOk() throws Exception {
            //given
            User user = User.of("admin", "password", Role.ADMIN);
            session.setAttribute(SessionConst.KEY.name(), UserPrincipal.from(user));
            UUID id = UUID.randomUUID();

            ProductUpdateRequest requestDto = new ProductUpdateRequest(
                    "product",
                    "des",
                    5,
                    1000,
                    LocalDateTime.of(
                            LocalDate.of(2023, Month.MARCH, 30),
                            LocalTime.MIDNIGHT
                    )
            );

            String requestBody = objectMapper.writeValueAsString(requestDto);

            //when
            ResultActions response = mockMvc.perform(
                    patch(BASE_URL + "/" + id)
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response.andExpect(status().isOk());
            verify(productService).updateProduct(any(), any());
        }
    }
}