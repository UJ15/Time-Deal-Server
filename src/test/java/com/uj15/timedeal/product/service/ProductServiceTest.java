package com.uj15.timedeal.product.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.uj15.timedeal.product.controller.dto.ProductCreateRequest;
import com.uj15.timedeal.product.repository.ProductRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Nested
    @DisplayName("createProduct 메서드 테스트")
    class DescribeCreateProduct {

        @ParameterizedTest
        @NullSource
        @DisplayName("null을 인자로 받을 경우")
        void itThrowIllegalArgumentException(ProductCreateRequest request) {
            //then
            Assertions.assertThatThrownBy(() -> productService.createProduct(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("dto를 인자로 받을 경우")
        void itCallRepositorySave() {
            //given
            ProductCreateRequest requestDto = new ProductCreateRequest(
                    "product",
                    "description",
                    1000,
                    LocalDateTime.now()
            );

            //when
            productService.createProduct(requestDto);

            //then
            verify(productRepository).save(any());
        }
    }
}